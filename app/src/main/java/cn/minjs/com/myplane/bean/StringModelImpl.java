/*
 * Copyright 2017 lizhaotailang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.minjs.com.myplane.bean;

import android.content.Context;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.toolbox.StringRequest;
import cn.minjs.com.myplane.interfaze.OnStringListener;
import cn.minjs.com.myplane.uitls.VolleySingleton;
/**
 * Created by Lizhaotailang on 2016/9/15.
 */

public class StringModelImpl {

    private Context context;

    public StringModelImpl(Context context) {
        this.context = context;
    }

    public void load(String url, final OnStringListener listener) {
        StringRequest request = new StringRequest(url, s -> listener.onSuccess(s), volleyError -> listener.onError(volleyError));

        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getVolleySingleton(context).addToRequestQueue(request);
    }

}
