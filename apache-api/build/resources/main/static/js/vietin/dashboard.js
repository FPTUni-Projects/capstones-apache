/**
 *  File: dashboard.js
 *  @author khal
 */
$(window).on('load', function () {
    DASHBOARD.mounted();
});

/**
 * Dashboard
 */
const DASHBOARD = {
    userInfo: {},
    cookieInfo: {},

    mounted() {
        COMMON_API.getCookieInfo().then(response => {
            COMMON.removeSidebar(response.rid)
            DASHBOARD.cookieInfo = response

            return response
        }).then(response => {
            if (response && response.uid) {
                return COMMON_API.getUserInfo(response.uid)
            }
            return null
        }).then(response => {
            if (response) {
                DASHBOARD.userInfo = response
                DASHBOARD.initPage()
            }
        })
    },

    initPage () {
        DASHBOARD.getAllOrder()
    },

    getAllOrder () {
        return REST.get('/vi/order/api/v1/get-all-order')
            .then(response => {
                if (response && response.length !== 0) {
                    $('#orderList').empty()
                    response.forEach((item, index) => {
                        let statusHtml = '<span class="main-badge main-badge-fill main-badge-BADGE_COLOR">STATUS_VALUE</span>'
                        switch (item.status) {
                            case CONSTANT.ORDER_STATUS.NEW_ORDER.id:
                                statusHtml = statusHtml.replace('BADGE_COLOR', 'info')
                                break
                            case CONSTANT.ORDER_STATUS.MANUFACTURING.id:
                                statusHtml = statusHtml.replace('BADGE_COLOR', 'primary')
                                break
                            case CONSTANT.ORDER_STATUS.APPROVAL.id:
                                statusHtml = statusHtml.replace('BADGE_COLOR', 'success')
                                break
                            case CONSTANT.ORDER_STATUS.BUILDING.id:
                                statusHtml = statusHtml.replace('BADGE_COLOR', 'warning')
                                break
                            case CONSTANT.ORDER_STATUS.FINISHED.id:
                                statusHtml = statusHtml.replace('BADGE_COLOR', 'danger')
                                break
                        }
                        statusHtml = statusHtml.replace('STATUS_VALUE', COMMON.getValueByKey(CONSTANT.ORDER_STATUS, 'id', 'name', item.status))
                        $('#orderList').append(`
                                                <tr>
                                                    <td>${index + 1}</td>
                                                    <td>${item.id}</td>
                                                    <td>${item.namePresentA}</td>
                                                    <td>${item.namePresentB}</td>
                                                    <td>${item.publicationQuantity}</td>
                                                    <td>${statusHtml}</td>
                                                    <td>${item.lastModified}</td>
                                                    <td>
                                                        <i class="pe-7s-info" onclick="DASHBOARD.getOrderInfo('${item.id}')"></i>
                                                    </td>
                                                </tr>`)
                    })
                } else {
                    COMMON.toastMsg('Chưa có đơn hàng nào được tạo.', CONSTANT.MSG_TYPE.WARNING)
                }
            }).catch(error => {
                COMMON.toastMsg('Hệ thống xử lý lỗi.', CONSTANT.MSG_TYPE.ERROR)
            })
    },

    getOrderInfo (_id) {
        $('#orderInfoModal').modal('show')
    }
}