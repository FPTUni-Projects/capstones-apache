/**
 *  File: create-user.js
 *
 */
$(window).on('load', function () {
    CREATE_RULE.mounted();
});

/**
 * CREATE-USER
 */
const CREATE_RULE = {
    cookieInfo: {},

    mounted () {
        COMMON_API.getCookieInfo().then(response => {
            COMMON.removeSidebar(response.rid)
            CREATE_RULE.cookieInfo = response
        })
    },

    createRule () {
        let ruleName = $('#ruleName').val()
        let ruleFile = $('#ruleFile').prop('files')
        let ruleDescription = $('#ruleDescription').val()

        console.log(CREATE_RULE.cookieInfo)

        if (!ruleName) {
            COMMON.toastMsg('Rule\'s name cannot be empty.', CONSTANT.MSG_TYPE.WARNING)
        } else if (!ruleFile || ruleFile.length === 0) {
            COMMON.toastMsg('File rule doesn\'t choose.', CONSTANT.MSG_TYPE.WARNING)
        } else if (!ruleDescription) {
            COMMON.toastMsg('Rule\'s description cannot be empty.', CONSTANT.MSG_TYPE.WARNING)
        } else {
            let formData = new FormData()
            formData.append('id', '-')
            formData.append('userId', CREATE_RULE.cookieInfo.uid)
            formData.append('name', ruleName)
            formData.append('publisher', '-')
            formData.append('file', ruleFile && ruleFile.length !== 0 ? ruleFile[0] : null)
            formData.append('fileName', ruleFile && ruleFile.length !== 0 ? ruleFile[0].name : '')
            formData.append('status', CONSTANT.RULE_STATUS.READY.id)
            formData.append('description', ruleDescription)

            REST.upload('/vi/rule/api/v1/create-rule', formData)
                .then(response => {
                    alert(response)
                    if (response) {
                        COMMON.toastMsg('Create rule success.', CONSTANT.MSG_TYPE.SUCCESS)
                        CREATE_RULE.clearCreateRuleForm()
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
                        CREATE_RULE.clearCreateRuleForm()
                    }
                })
        }
    },

    clearCreateRuleForm () {
        $('#ruleName').val('')
        $('#ruleFile').val('')
        $('#ruleDescription').val('')
    }
}

/* [Create Rule] */
$('#btnCreateRule').click(function () { CREATE_RULE.createRule() })

/* [Cancel Create Rule] */
$('#btnCancelCreateRule').click(function () { CREATE_RULE.clearCreateRuleForm() })