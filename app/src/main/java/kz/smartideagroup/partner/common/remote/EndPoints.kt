package kz.smartideagroup.partner.common.remote

object EndPoints {
    const val GET_HISTORY = "/api/a/cb/purchase/history"
    const val GET_ORDER_ACTIVE = "/api/a/cb/delivery/orders/active"
    const val GET_ORDER_COMPLETED = "/api/a/cb/delivery/orders/completed"
    const val GET_ORDER_ID = "/api/a/cb/delivery/orders"
    const val POST_STATUS = "/api/a/cb/delivery/orders/status"
    const val GET_CATEGORIES = "/api/a/cb/retail/categories"
    const val POST_STATUS_RETAIL = "/api/a/cb/retail/status"
    const val GET_STATUS_RETAIL = "/api/a/cb/retail/info"
    const val POST_DISHES_STATUS = "/api/a/cb/retail/dishes/status"
    const val POST_CASH_BACK_HISTORY = "/api/v1/cash-back/history"
    const val POST_NOTIFICATION = "/api/v1/retail/notification"
    const val POST_SIGN_IN = "/api/s/v1/manual/token/retail"
    const val POST_FIREBASE_TOKEN = "/api/v1/retail/token"

}