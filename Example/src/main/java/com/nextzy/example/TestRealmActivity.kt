package com.nextzy.example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.CompoundButton
import com.nextzy.library.base.delegate.DefaultSnackbarDelegate
import com.nextzy.library.base.mvvm.layer3Repository.database.realm.BaseRealmDatabase
import com.nextzy.library.extension.view.hideKeyboard
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_test_realm.*

class TestRealmActivity : AppCompatActivity() {

    private var database: CustomDatabase = CustomDatabase.instance
    private lateinit var snackbar: DefaultSnackbarDelegate
    private var mode: Long = BaseRealmDatabase.ASYNC


    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CustomDatabase.instance.initDatabase(this)
        setContentView(R.layout.activity_test_realm)
        snackbar = DefaultSnackbarDelegate(this)
        database
                .findAllMockObject(mode)
                .subscribe(
                        consumerFindAllObject(),
                        consumerException())


        btn_find.setOnClickListener(onClickListener())
        btn_add.setOnClickListener(onClickListener())
        btn_delete.setOnClickListener(onClickListener())
        btn_find_all.setOnClickListener(onClickListener())
        tbtn_sync_async.setOnCheckedChangeListener(onCheckedChangeListener())
    }


    private fun onCheckedChangeListener(): (CompoundButton, Boolean) -> Unit {
        return { compoundButton, b ->
            mode = if (b) {
                BaseRealmDatabase.ASYNC
            } else {
                BaseRealmDatabase.SYNC
            }
        }
    }

    private fun onClickListener(): View.OnClickListener {
        return View.OnClickListener { v ->
            when (v) {
                btn_find -> {
                    database
                            .findMockObject(mode, edt_id.text.toString())
                            .subscribe(
                                    consumerFindObject(),
                                    consumerException())
                }
                btn_add -> {
                    database
                            .saveMockObject(mode, createMockObject())
                            .subscribe(
                                    consumerAddObject(),
                                    consumerException())
                }
                btn_delete -> {
                    database
                            .deleteMockObject(mode, edt_id.text.toString())
                            .subscribe(
                                    consumerDeleteObject(),
                                    consumerException())
                }
                btn_find_all -> {
                    database
                            .findAllMockObject(mode)
                            .subscribe(
                                    consumerFindAllObject(),
                                    consumerException())
                }
            }
        }
    }

    private fun updateView() {
        database.findAllMockObject(BaseRealmDatabase.ASYNC)
                .subscribe(consumerFindAllObject(),
                           consumerException())
        hideKeyboard(edt_id)
    }

    private fun createMockObject(): MockObject
            = MockObject()

    private fun consumerFindObject(): Consumer<MockObject> {
        return Consumer { mockObject ->
            tv_message.text = mockObject.toString()
        }
    }

    private fun consumerDeleteObject(): Consumer<MockObject> {
        return Consumer { trasaction ->
            updateView()
        }
    }

    private fun consumerAddObject(): Consumer<MockObject> {
        return Consumer { mockObject ->
            updateView()
        }
    }


    private fun consumerFindAllObject(): Consumer<MutableList<MockObject>> {
        return Consumer { allMockObject ->
            var str = ""
            allMockObject.forEach({ str += "$it\n" })
            tv_message.text = str
        }
    }

    private fun consumerException(): Consumer<Throwable> {
        return Consumer { e ->
            e.message?.let { snackbar.showSnackbarErrorDismiss(it) }
            hideKeyboard(edt_id)
            updateView()
        }
    }
}
