package com.org.api.vietin.common.constant;

/**
 * DataConstant
 *
 * @author khal
 * @since 2020/11/28
 */
public interface DataConstant {

    /**
     * Delete flag
     * 0: not delete
     * 1: deleted
     */
    String[] DEL_FLG = {"0", "1"};

    /**
     * Create order mode
     * 0: create
     * 1: edit
     */
    String[] CREATE_ORDER_MODE = {"0", "1"};

    /**
     * Order status
     * 0: Create new -> Complete order
     * 1: Confirm
     * 2: Approve
     * 3: Building
     * 4: Finish
     */
    String[] ORDER_STATUS = {"0", "1", "2", "3", "4"};



}
