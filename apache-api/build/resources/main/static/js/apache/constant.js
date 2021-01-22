/**
 *  File: constant.js
 *
 */

/**
 * CONSTANT
 */
const CONSTANT = {

    MSG_TYPE: {
        INFO: 'info',
        WARNING: 'warning',
        ERROR: 'danger',
        SUCCESS: 'success'
    },

    ROLES: {
        ADMIN: {id: '0', name: 'Administrator'},
        USER: {id: '1', name: 'User'},
    },

    USER_STATUS: {
        ACTIVE: {id: '0', name: "Active"},
        BLOCKED: {id: '1', name: "Blocked"},
    },

    SERVER_STATUS: {
        ENABLED: {id: '0', name: 'Enabled'},
        DISABLED: {id: '1', name: 'Disabled'}
    },

    RULE_STATUS: {
        ENABLED: {id: '0', name: 'Enabled'},
        DISABLED: {id: '1', name: 'Disabled'}
    }

}