package app.pillikan.kz.common.remote

object Constants {
//    const val BASE_URL = "https://java.pillikan.org.kz"
    const val BASE_URL = "https://java.pillikan.kz"
    const val BASE_IMAGE_URL = "https://st.pillikan.kz//delivery/"
    const val CLIENT_ID = "cmV0YWls"
    const val DEFAULT_NOTIFICATION_CHANNEL_ID = "default_notification_channel_id"
    const val CONTENT_TYPE = "application/json"

    const val RESPONSE_SUCCESS_CODE = 200

    const val PAGE_LIMIT = 10

    const val SERIALIZED_ORDER_COMPLETED = "SERIALIZED_ORDER_COMPLETED"

    //FireBase
    const val TYPE = "type"
    const val NAME = "name"
    const val DATA = "data"
    const val PHONE = "phone"
    const val ORDER_ID = "orderId"
    const val PUSH_ORDER_ID = "PushOrderId"
    const val PUSH_ORDER_NAME = "PushPayName"
    const val PUSH_ORDER_PHONE = "PushPayPhone"
    const val PUSH_DELIVERY = 3
    const val PUSH_BY_MYSELF = 2

    //TYPE ORDER
    const val DELIVERY = 1
    const val BY_MYSELF = 2

    //STATUS ORDER
    const val ORDER_PAY = 2
    const val ORDER_COOKING = 3
    const val ORDER_SEND = 4
    const val ORDER_DELIVERED= 5
    const val ORDER_FINISH = 6

    //Adapter
    const val ZERO = 0
    const val ONE = 1
    const val TWO = 2
    const val HUNDRED = 100
    const val ONE_THOUSAND:Long = 1000
    const val SIXTY = 60

    //TAB BAR LAYOUT
    const val ALL_TAB = "Все"
    const val PAY_TAB = "Pay"
    const val DELIVERY_TAB = "Доставка"

    //Retail delivery status
    const val DELIVERY_CLOSE = 2
    const val DELIVERY_OPEN = 1

    //Put Extra Order
    const val ACCEPT_ORDER_ID = "ACCEPT_ORDER_ID"

    //Timer
    var TIME_LEFT_IN_MILLIS = 200000
    var TIME_MILLIS = 180000
    var DELAY = 2000

    //Order Text
    const val UTENSILS_COUNT = "Приборы  x"
    const val ORDER = "Заказ "
    const val TIME_DEFAULT = "00:00"
    const val DOT = "."
    const val PLUS = "+"
    const val MINUS = "-"
    const val BRACKET = ")"

    const val THREE = 3

    //Time Cook
    const val TEN_MINUTE = 10
    const val TWENTY_MINUTE = 20
    const val THIRTY_MINUTE = 30
    const val FORTY_MINUTE = 40
    const val FIFTY_MINUTE = 50

    //Date
    var dayFrom = 0
    var monthFrom = 0
    var yearFrom = 0
    var dayTo = 0
    var monthTo = 0
    var yearTo = 0

    const val ZERO_FLOAT = 0f
    const val ONE_FLOAT = 1f
    const val TWO_FLOAT = 2f

    const val PHONE_NUMBER = "+77750074230"
    const val INSTAGRAM_LINK = "https://www.instagram.com/pillikan.kz/"
    const val YOUTUBE_LINK = "https://www.youtube.com/channel/UCnJQ6k3GfN5tpKQMQ2v-ZrQ"
    const val FAQ_LINK = "https://pillikan.kz/partners-faq"

    //Calculate
    const val ONE_STRING = "1"
    const val TWO_STRING = "2"
    const val THREE_STRING = "3"
    const val FOUR_STRING = "4"
    const val FIVE_STRING = "5"
    const val SIX_STRING = "6"
    const val SEVEN_STRING = "7"
    const val EIGHT_STRING = "8"
    const val NINE_STRING = "9"
    const val ZERO_STRING = "0"

    //Transaction minimum sum
    const val MIN_SUM = 30
    const val TRANSACTION_SUM = "TRANSACTION_SUM"

    //
    const val INTENT_NAV = "nav"
    const val SDK_24 = 24
    const val REQUEST_CODE = 2323
    const val PACKAGE = "package:"

}