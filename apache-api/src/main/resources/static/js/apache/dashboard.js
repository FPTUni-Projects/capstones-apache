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
        DASHBOARD.getAllRule()
    },

    getAllRule () {
        return REST.get('/vi/rule/api/v1/get-all-rule', {
            userId: DASHBOARD.cookieInfo.uid,
            roleId: DASHBOARD.cookieInfo.rid
        })
        .then(response => {
            if (response && response.length !== 0) {
                $('#ruleList').empty()
                response.forEach((item, index) => {
                    let statusHtml = '<span class="main-badge main-badge-fill main-badge-BADGE_COLOR">STATUS_VALUE</span>'
                    switch (item.status) {
                        case CONSTANT.RULE_STATUS.READY.id:
                            statusHtml = statusHtml.replace('BADGE_COLOR', 'info')
                            break
                        case CONSTANT.RULE_STATUS.ENABLED.id:
                            statusHtml = statusHtml.replace('BADGE_COLOR', 'success')
                            break
                        case CONSTANT.RULE_STATUS.DISABLED.id:
                            statusHtml = statusHtml.replace('BADGE_COLOR', 'danger')
                            break
                    }
                    statusHtml = statusHtml.replace('STATUS_VALUE', COMMON.getValueByKey(CONSTANT.RULE_STATUS, 'id', 'name', item.status))

                    let readyChecked = item.status === CONSTANT.RULE_STATUS.READY.id ? 'checked' : ''
                    let enabledChecked = item.status === CONSTANT.RULE_STATUS.ENABLED.id ? 'checked' : ''
                    let disabledChecked = item.status === CONSTANT.RULE_STATUS.DISABLED.id ? 'checked' : ''

                    $('#ruleList').append(`
                                            <tr style="width: 100%">
                                                <td style="width: 5%;">${index + 1}</td>
                                                <td style="width: 10%;word-break: break-word;">${item.name}</td>
                                                <td style="width: 27%;word-break: break-word;">${item.description}</td>
                                                <td style="width: 7%;word-break: break-word;">${statusHtml}</td>
                                                <td style="width: 10%;word-break: break-word;">${item.publisher}</td>
                                                <td style="width: 10%;word-break: break-word;">
                                                    <button class="btn-main" onclick="DASHBOARD.downloadRule('${item.id}', '${item.userId}')">Download Rules</button>
                                                </td>
                                                <td class="form-group" style="width: 23%;word-break: break-word;">
                                                    <span style="font-size: 1.4rem; margin-right: 1.5rem;">
                                                        <input type="radio" name="ruleStatus${index}" onchange="DASHBOARD.updateRule('${item.id}', '${item.userId}', '0')"
                                                               id="ruleStatusReady${index}" value="0" ${readyChecked} /> 
                                                        <label style="font-weight: bold;" class="text-info" for="ruleStatusReady${index}">Ready</label>
                                                    </span>
                                                    <span style="font-size: 1.4rem; margin-right: 1.5rem; font-weight: bold;" class="text-success">
                                                        <input type="radio" name="ruleStatus${index}" onchange="DASHBOARD.updateRule('${item.id}', '${item.userId}', '1')"
                                                               id="ruleStatusEnabled${index}" value="1" ${enabledChecked} /> 
                                                        <label style="font-weight: bold;" class="text-success" for="ruleStatusEnabled${index}">Enabled</label>
                                                    </span>
                                                    <span style="font-size: 1.4rem; margin-right: 1.5rem; font-weight: bold;" class="text-danger">
                                                        <input type="radio" name="ruleStatus${index}" onchange="DASHBOARD.updateRule('${item.id}', '${item.userId}', '2')"
                                                               id="ruleStatusDisabled${index}" value="2" ${disabledChecked} /> 
                                                        <label style="font-weight: bold;" class="text-danger" for="ruleStatusDisabled${index}">Disabled</label>
                                                    </span>
                                                </td>
                                                <td style="width: 13%;word-break: break-word;">
                                                    <button class="btn-main" onclick="DASHBOARD.deleteRule('${item.id}', '${item.userId}')">Delete</button>
                                                </td>
                                            </tr>`)
                })
            } else {
                COMMON.toastMsg('Chưa có rule nào được tạo.', CONSTANT.MSG_TYPE.WARNING)
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