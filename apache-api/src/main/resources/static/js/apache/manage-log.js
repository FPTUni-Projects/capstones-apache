/**
 *  File: dashboard.js
 *
 */
$(window).on('load', function () {
    MANAGE_LOG.mounted();
});

/**
 * Dashboard
 */
const MANAGE_LOG = {
    userInfo: {},
    cookieInfo: {},

    mounted() {
        COMMON_API.getCookieInfo().then(response => {
            COMMON.removeSidebar(response.rid)
            MANAGE_LOG.cookieInfo = response

            return response
        }).then(response => {
            if (response && response.uid) {
                return COMMON_API.getUserInfo(response.uid)
            }
            return null
        }).then(response => {
            if (response) {
                MANAGE_LOG.userInfo = response
                MANAGE_LOG.initPage()
            }
        })
    },

    initPage () {
        MANAGE_LOG.getAllLog()
    },

    getAllLog () {
        return REST.get('/vi/log/api/v1/get-all-log').then(response => {
            if (response && response.length !== 0) {
                response.forEach((item, index) => {
                    $('#logList').append(`
                                    <tr style="width: 100%;">
                                        <td style="width: 5%;">${index + 1}</td>
                                        <td style="width: 15%;">${item.time}</td>
                                        <td style="width: 15%;">${item.error}</td>
                                        <td style="width: 15%;">${item.host}</td>
                                        <td style="width: 50%; word-break: break-word;">${item.description}</td>
                                    </tr>`)
                })
            }
        })
    }

}