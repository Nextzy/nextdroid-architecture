/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nextzy.nextwork.engine.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * A generic class that holds a value with its loading status.
 *
 * @param <T>
 */
public class NextworkRequest<T> {

    @NonNull
    public final boolean isForceFetch;

    @Nullable
    public final T data;

    public NextworkRequest( @Nullable T data, @Nullable boolean isForceFetch) {
        this.data = data;
        this.isForceFetch = isForceFetch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NextworkRequest<?> request = (NextworkRequest<?>) o;

        if (isForceFetch != request.isForceFetch) return false;
        return data != null ? data.equals(request.data) : request.data == null;
    }

    @Override
    public int hashCode() {
        int result = (isForceFetch ? 1 : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NextworkRequest{" +
                "isForceFetch=" + isForceFetch +
                ", data=" + data +
                '}';
    }
}
