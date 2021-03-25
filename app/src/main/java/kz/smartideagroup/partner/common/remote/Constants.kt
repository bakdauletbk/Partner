package kz.smartideagroup.partner.common.remote

object Constants {
    const val BASE_URL = "https://java.pillikan.org.kz"
    const val CLIENT_ID = "cmV0YWls"

    const val RESPONSE_SUCCESS_CODE = 200

    const val PAGE_LIMIT = 10

    const val SERIALIZED_ORDER_COMPLETED = "SERIALIZED_ORDER_COMPLETED"

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
    const val ACCEPT_ORDER = "ACCEPT_ORDER"

    //Timer
    var TIME_LEFT_IN_MILLIS = 200000
    var TIME_MILLIS = 180000

    //Order Text
    const val UTENSILS_COUNT = "Приборы  x"
    const val ORDER = "Заказ "
    const val TIME_DEFAULT = "00:00"
    const val DOT = "."
    const val PLUS = "+"
    const val MINUS = "-"

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

    //
    const val ONE_FLOAT = 1f
    const val TWO_FLOAT = 2f
}