/**
 *  File: common-api.js
 *
 */

/**
 * COMMONAPI
 */
const COMMON_API = {

    getCookieInfo () {
        return REST.get('/vi/auth/api/v1/auth/get-cookie-info')
            .then(response => {
                if (response && response.status) {
                    return response
                } else {
                    location.reload()
                }
            }).catch(error => {
                location.reload()
            })
    },

    logout () {
        return REST.get('/vi/auth/api/v1/auth/logout')
            .then(response => {
                if (response) {
                    location.reload()
                }
            }).catch(error => {
                console.log(error)
                location.reload()
            })
    },

    getUserInfo (_userId) {
        return REST.get('/vi/user/api/v1/get-user-info', {userId: _userId})
            .then(response => {
                if (response) {
                    return response
                }
            }).catch(error => {
                COMMON.toastMsg('Hệ thống xử lý lỗi.', CONSTANT.MSG_TYPE.ERROR)
            })
    }

}

/* [Logout] */
$('#logoutNavbar').click(function () { COMMON_API.logout() })
$('#logoutSidebar').click(function () { COMMON_API.logout() })