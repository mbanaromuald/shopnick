package com.theleafapps.pro.shopnick.tasks;

import android.content.Context;
import android.util.Log;

import com.theleafapps.pro.shopnick.models.multiples.CartItems;
import com.theleafapps.pro.shopnick.utils.AppConstants;
import com.theleafapps.pro.shopnick.utils.PrefUtil;

import dfapi.ApiException;
import dfapi.ApiInvoker;
import dfapi.BaseAsyncRequest;

/**
 * Created by aviator on 30/06/16.
 */
public class AddCartItemTask extends BaseAsyncRequest {

    public int cartItemId;
    Context context;
    CartItems cartItems;

    public AddCartItemTask(Context context, CartItems cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @Override
    protected void doSetup() throws ApiException {
        callerName = "AddCartItemTask";

        serviceName = AppConstants.DB_SVC;
        endPoint = "cart_item";

        verb = "POST";

        requestString = ApiInvoker.serialize(cartItems).replace("\"cart_item_id\":0,", "");
        requestString = requestString.replace(",\"cart_item_id\":0", "");

        requestString = requestString.replace(",\"customer_id\":0", "");
        requestString = requestString.replace("\"customer_id\":0,", "");

        applicationApiKey = AppConstants.API_KEY;
        sessionToken = PrefUtil.getString(context, AppConstants.SESSION_TOKEN);
    }

    @Override
    protected void processResponse(String response) throws ApiException, org.json.JSONException {
        // response has whole contact record, but we just want the id
        CartItems cartItems = (CartItems) ApiInvoker.deserialize(response, "", CartItems.class);
        cartItemId = cartItems.cartItemList.get(0).cart_item_id;
    }

    @Override
    protected void onCompletion(boolean success) {
        if (success) {
            Log.d("Tang Ho", "Success");
        }
    }
}
