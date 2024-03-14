package com.example.artel.EtoBaza

object EndPoint {
    val URL_ROOT = "http://192.168.43.86/Artel/v1/Api.php?apicall="
    var URL_CREATE_USER = URL_ROOT + "createuser"
    val URL_GET_USER = URL_ROOT + "getuser"
    val URL_UPDATE_USER = URL_ROOT + "updatehero";
    val URL_DELETE_USER = URL_ROOT + "deleteuser&id=";
}