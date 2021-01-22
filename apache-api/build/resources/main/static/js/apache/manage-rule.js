/**
 *  File: create-user.js
 *
 */
$(window).on('load', function () {
    MANAGE_RULE.mounted();
});

/**
 * CREATE-USER
 */
const MANAGE_RULE = {
    cookieInfo: {},

    mounted () {
        COMMON_API.getCookieInfo().then(response => {
            COMMON.removeSidebar(response.rid)
            MANAGE_RULE.cookieInfo = response
            MANAGE_RULE.getAllRule()
        })
    },

    createRule () {
        let ruleName = $('#ruleName').val()
        let ruleFile = $('#ruleFile').prop('files')

        if (!ruleName) {
            COMMON.toastMsg('Rule\'s name cannot be empty.', CONSTANT.MSG_TYPE.WARNING)
        } else if (!ruleFile || ruleFile.length === 0) {
            COMMON.toastMsg('File rule doesn\'t choose.', CONSTANT.MSG_TYPE.WARNING)
        } else {
            let formData = new FormData()
            formData.append('id', '-')
            formData.append('name', ruleName)
            formData.append('file', ruleFile && ruleFile.length !== 0 ? ruleFile[0] : null)
            formData.append('fileName', ruleFile && ruleFile.length !== 0 ? ruleFile[0].name : '')
            formData.append('status', CONSTANT.RULE_STATUS.ENABLED.id)

            REST.upload('/vi/rule/api/v1/create-rule', formData)
                .then(response => {
                    alert(response)
                    if (response) {
                        COMMON.toastMsg('Create rule success.', CONSTANT.MSG_TYPE.SUCCESS)
                        MANAGE_RULE.clearCreateRuleForm()
                        MANAGE_RULE.getAllRule()
                    } else {
                        alert('a')
                        COMMON.toastMsg('Create rule failed.', CONSTANT.MSG_TYPE.ERROR)
                    }
                })
                .catch(error => {
                    if (error.responseText) {
                        COMMON.toastMsg('Create rule failed.', CONSTANT.MSG_TYPE.ERROR)
                    } else {
                        COMMON.toastMsg('Create rule success.', CONSTANT.MSG_TYPE.SUCCESS)
                        MANAGE_RULE.clearCreateRuleForm()
                        MANAGE_RULE.getAllRule()
                    }
                })
        }
    },

    getAllRule () {
        REST.get('/vi/rule/api/v1/get-all-rule')
            .then(response => {
                if (response && response.length !== 0) {
                    $('#ruleLst').empty()

                    response.forEach((rule, index) => {
                        let btnName = rule.status === CONSTANT.RULE_STATUS.ENABLED.id ? 'Disabled' : 'Enabled'
                        let btnColor = rule.status === CONSTANT.RULE_STATUS.ENABLED.id ? '#f00' : '#0f0'

                        $('#ruleLst').append(`
                                            <tr style="width: 100%">
                                                <td>${index + 1}</td>
                                                <td style="word-break: break-word;">${rule.name}</td>
                                                <td style="word-break: break-word;">${rule.fileName}</td>
                                                <td style="word-break: break-word;">
                                                    <button class="btn-main" style="font-size: 1.4rem;background: ${btnColor}; color: #fff;"
                                                            onclick="MANAGE_RULE.updateRule('${rule.id}', '${rule.status}')">${btnName}</button>
                                                </td>
                                                <td style="width: 13%;word-break: break-word;">
                                                    <button class="btn-main" onclick="MANAGE_RULE.downloadRule('${rule.id}')">Download Rule</button>
                                                </td>
                                            </tr>`)
                    })
                } else {
                    COMMON.toastMsg('List rule is empty!', CONSTANT.MSG_TYPE.WARNING)
                }
            })
    },

    updateRule (_ruleId, _status) {
        REST.get('/vi/rule/api/v1/update-rule', {
            ruleId: _ruleId,
            status: _status === CONSTANT.RULE_STATUS.ENABLED.id ? CONSTANT.RULE_STATUS.DISABLED.id :
                CONSTANT.RULE_STATUS.ENABLED.id
        })
            .then(response => {
                if (response) {
                    MANAGE_RULE.MANAGE_RULE()
                    COMMON.toastMsg('Update rule success.', CONSTANT.MSG_TYPE.SUCCESS)
                } else {
                    COMMON.toastMsg('Update rule failed.', CONSTANT.MSG_TYPE.ERROR)
                }
            }).catch(error => {
            if (error.responseText) {
                COMMON.toastMsg('Update rule failed.', CONSTANT.MSG_TYPE.ERROR)
            } else {
                MANAGE_RULE.getAllRule()
                COMMON.toastMsg('Update rule success.', CONSTANT.MSG_TYPE.SUCCESS)
            }
        })
    },

    downloadRule (_ruleId) {
        REST.get('/vi/rule/api/v1/download-rule', {ruleId: _ruleId})
        .then(response => {
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
    },

    clearCreateRuleForm () {
        $('#ruleName').val('')
        $('#ruleFile').val('')
        $('#ruleDescription').val('')
    }
}

/* [Create Rule] */
$('#btnCreateRule').click(function () { MANAGE_RULE.createRule() })

/* [Cancel Create Rule] */
$('#btnCancelCreateRule').click(function () { MANAGE_RULE.clearCreateRuleForm() })