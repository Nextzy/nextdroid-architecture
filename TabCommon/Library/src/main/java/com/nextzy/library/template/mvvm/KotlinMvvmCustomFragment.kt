package th.co.thekhaeng.waterlibrary.kotlin.template.mvvm


import android.os.Bundle
import com.nextzy.library.customize.mvvm.CustomMvvmFragment


/**
 * Created by TheKhaeng on 12/17/2016.
 */

class KotlinMvvmCustomFragment : CustomMvvmFragment<KotlinMvvmCustomViewModel>() {

    companion object {
        fun newInstance(): KotlinMvvmCustomFragment {
            val fragment = KotlinMvvmCustomFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }


    override
    fun setupViewModel(): Class<KotlinMvvmCustomViewModel>?
            = KotlinMvvmCustomViewModel::class.java

    override
    fun setupLayoutView(): Int = 0

    override
    fun setupInstance() {

    }

    override
    fun setupView() {

    }

    override
    fun initialize() {

    }

    override
    fun onRestoreArgument(bundle: Bundle) {
        super.onRestoreArgument(bundle)
    }

    override
    fun onRestoreView(savedInstanceState: Bundle) {
        super.onRestoreView(savedInstanceState)
    }

    override
    fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
    }

    override
    fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }

}

