package com.farsitel.bazaar.auth.connection

import android.os.Bundle
import com.farsitel.bazaar.ErrorResponse
import com.farsitel.bazaar.auth.model.BazaarSignInAccount

object AuthResponseHandler {

    private const val KEY_IAL_STATUE: String = "ialStatus"
    private const val KEY_IAL_ERROR_MESSAGE: String = "ialErrorMessage"
    private const val KEY_IAL_ACCOUNT_INFO_AID: String = "aid"
    private const val KEY_IAL_ACCOUNT_INFO_NICKNAME: String = "nickname"

    private const val ERROR_MESSAGE_UNKNOWN = "unknown error"

    private const val IAL_STATUE_SUCCESS: Int = 0
    private const val IAL_STATUE_FAILED: Int = -1
    private const val IAL_STATUE_DEVELOPER_ERROR: Int = -2
    private const val IAL_STATUE_ACCOUNT_NOT_EXISTS: Int = -3

    fun isSuccessful(extras: Bundle): Boolean {
        return extras.getInt(KEY_IAL_STATUE) == IAL_STATUE_SUCCESS
    }

    fun getErrorResponse(extras: Bundle): ErrorResponse {
        val errorCode = extras.getInt(KEY_IAL_STATUE)
        val message = when (errorCode) {
            IAL_STATUE_FAILED,
            IAL_STATUE_DEVELOPER_ERROR -> extras.getString(KEY_IAL_ERROR_MESSAGE)
            IAL_STATUE_ACCOUNT_NOT_EXISTS -> "Account not exists for packageName"
            else -> ERROR_MESSAGE_UNKNOWN
        } ?: ERROR_MESSAGE_UNKNOWN

        return ErrorResponse(message, errorCode)
    }

    fun getAccountByBundle(extras: Bundle): BazaarSignInAccount? {

        val accountId = extras.getString(KEY_IAL_ACCOUNT_INFO_AID) ?: return null

        // in first version we don't this
        // val nickname = extras.getString(IAL_ACCOUNT_INFO_NICKNAME_KEY)

        return BazaarSignInAccount(accountId)
    }
}