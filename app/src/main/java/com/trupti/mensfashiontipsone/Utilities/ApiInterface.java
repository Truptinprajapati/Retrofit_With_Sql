package com.trupti.mensfashiontipsone.Utilities;

import com.trupti.mensfashiontipsone.Response.AppDialogResponse;
import com.trupti.mensfashiontipsone.Response.AppListResponse;
import com.trupti.mensfashiontipsone.Response.AppOnlineDialogResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by vaksys-android-52 on 25/7/17.
 */

public interface ApiInterface {
    @GET(AppConfig.LIST_URL)
    Call<AppListResponse> APP_LIST_RESPONSE_CALL();

    @GET(AppConfig.MORE_APPS)
    Call<AppOnlineDialogResponse>MORE_APPS_CALL ();

    @GET(AppConfig.TOP_APPS)
    Call<AppOnlineDialogResponse>TOP_APPS_CALL ();

    @GET(AppConfig.EXIT_APPS)
    Call<AppOnlineDialogResponse>EXIT_APPS_CALL ();

    @GET(AppConfig.ALERT_APPS)
    Call<AppDialogResponse> ALERT_APPS_CALL ();
}
