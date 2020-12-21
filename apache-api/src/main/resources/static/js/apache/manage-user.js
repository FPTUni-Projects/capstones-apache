/**
 *  File: dashboard.js
 *
 */
$(window).on('load', function () {
    MANAGE_USER.mounted();
});

/**
 * Dashboard
 */
const MANAGE_USER = {
    cookieInfo: {},

    mounted() {
        COMMON_API.getCookieInfo().then(response => {
            COMMON.removeSidebar(response.rid)
            MANAGE_USER.cookieInfo = response

            return response
        }).then(response => {
            if (response) {
                MANAGE_USER.initPage();
            }
        })
    },

    initPage () {
        MANAGE_USER.getAllUser()
    },

    getAllUser () {
        return REST.get('/vi/user/api/v1/get-all-user').then(response => {
            if (response && response.length !== 0) {
                $('#userList').empty()
                response.forEach((item, index) => {
                    $('#userList').append(`
                                    <tr style="width: 100%;">
                                        <td>${index + 1}</td>
                                        <td>${item.fullName}</td>
                                        <td>${item.username}</td>
                                        <td>${item.phoneNumber}</td>
                                        <td>USER</td>
                                        <td>
                                            <button class="btn-main" onclick="MANAGE_USER.deleteRule('${item.id}')">Delete</button>
                                        </td>
                                    </tr>`)
                })
            }
        })
    },

    deleteRule (_userId) {
        REST.get('/vi/user/api/v1/update-user-status', {
            userId: _userId
        })
            .then(response => {
                if (response) {
                    MANAGE_USER.getAllUser()
                    COMMON.toastMsg('Delete user success.', CONSTANT.MSG_TYPE.SUCCESS)
                } else {
                    COMMON.toastMsg('Delete user failed.', CONSTANT.MSG_TYPE.ERROR)
                }
            }).catch(error => {
            if (error.responseText) {
                COMMON.toastMsg('Delete user failed.', CONSTANT.MSG_TYPE.ERROR)
            } else {
                MANAGE_USER.getAllUser()
                COMMON.toastMsg('Delete user success.', CONSTANT.MSG_TYPE.SUCCESS)
            }
        })
    },
}