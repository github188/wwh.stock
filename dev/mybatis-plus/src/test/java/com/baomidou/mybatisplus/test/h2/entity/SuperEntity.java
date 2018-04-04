/**
 * Copyright (c) 2011-2014, hubin (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.baomidou.mybatisplus.test.h2.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;

/**
 * <p>
 * 测试父类情况
 * </p>
 *
 * @author hubin
 * @Date 2016-06-26
 */
public class SuperEntity implements Serializable {

    /* 主键ID 注解，value 字段名，type 用户输入ID */
    @TableId(value = "test_id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

