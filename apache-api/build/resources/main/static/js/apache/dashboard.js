/**
 *  File: dashboard.js
 *
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
        DASHBOARD.getAllUser()
    },

    getAllUser () {
        return REST.get('/vi/user/api/v1/get-all-user')
        .then(response => {
            if (response && response.length !== 0) {
                $('#userList').empty()
                response.forEach((item, index) => {
                    let statusHtml = '<span class="main-badge main-badge-fill main-badge-BADGE_COLOR">STATUS_VALUE</span>'
                    switch (item.status) {
                        case CONSTANT.USER_STATUS.USER_ACTIVE.id:
                            statusHtml = statusHtml.replace('BADGE_COLOR', 'success')
                            break
                        case CONSTANT.USER_STATUS.USER_INACTIVE.id:
                            statusHtml = statusHtml.replace('BADGE_COLOR', 'danger')
                            break
                    }
                    statusHtml = statusHtml.replace('STATUS_VALUE', COMMON.getValueByKey(CONSTANT.USER_STATUS, 'id', 'name', item.status))

                    let enabledChecked = item.status === CONSTANT.USER_STATUS.USER_ACTIVE.id ? 'checked' : ''
                    let disabledChecked = item.status === CONSTANT.USER_STATUS.USER_INACTIVE.id ? 'checked' : ''

                    $('#userList').append(`
                                            <tr style="width: 100%">
                                                <td>${index + 1}</td>
                                                <td style="word-break: break-word;">${item.fullName}</td>
                                                <td style="word-break: break-word;">${item.username}</td>
                                                <td style="word-break: break-word;">${item.serverName}</td>
                                                <td style="word-break: break-word;">${statusHtml}</td>
                                                <td style="width: 13%;word-break: break-word;">
                                                    <button class="btn-main" onclick="window.location.href = '/user-info?id=${item.id}'">View Info</button>
                                                </td>
                                            </tr>`)
                })
            } else {
                COMMON.toastMsg('Danh sách user trống!', CONSTANT.MSG_TYPE.WARNING)
            }
        }).catch(error => {
            COMMON.toastMsg('Hệ thống xử lý lỗi.', CONSTANT.MSG_TYPE.ERROR)
        })
    },

    deleteRule (_ruleId, _userId) {
        REST.get('/vi/rule/api/v1/remove-rule', {
            ruleId: _ruleId,
            userId: _userId
        })
        .then(response => {
            if (response) {
                DASHBOARD.getAllRule()
                COMMON.toastMsg('Delete rule success.', CONSTANT.MSG_TYPE.SUCCESS)
            } else {
                COMMON.toastMsg('Delete rule failed.', CONSTANT.MSG_TYPE.ERROR)
            }
        }).catch(error => {
            if (error.responseText) {
                COMMON.toastMsg('Delete rule failed.', CONSTANT.MSG_TYPE.ERROR)
            } else {
                DASHBOARD.getAllRule()
                COMMON.toastMsg('Delete rule success.', CONSTANT.MSG_TYPE.SUCCESS)
            }
        })
    },

    updateRule (_ruleId, _userId, _status) {
        REST.get('/vi/rule/api/v1/update-rule', {
            ruleId: _ruleId,
            userId: _userId,
            status: _status
        })
        .then(response => {
            if (response) {
                DASHBOARD.getAllRule()
                COMMON.toastMsg('Update rule success.', CONSTANT.MSG_TYPE.SUCCESS)
            } else {
                COMMON.toastMsg('Update rule failed.', CONSTANT.MSG_TYPE.ERROR)
            }
        }).catch(error => {
            if (error.responseText) {
                COMMON.toastMsg('Update rule failed.', CONSTANT.MSG_TYPE.ERROR)
            } else {
                DASHBOARD.getAllRule()
                COMMON.toastMsg('Update rule success.', CONSTANT.MSG_TYPE.SUCCESS)
            }
        })
    },

    downloadRule (_ruleId, _userId) {
        REST.get('/vi/rule/api/v1/download-rule', {
            ruleId: _ruleId,
            userId: _userId
        }).then(response => {
            if (response) {
                let a = document.createElement("a")
                a.href = response.base64
                a.download = response.name
                a.click(); //Downloaded file
            }
        })
        .catch(error => {
            console.log(error)

        })
    }
}