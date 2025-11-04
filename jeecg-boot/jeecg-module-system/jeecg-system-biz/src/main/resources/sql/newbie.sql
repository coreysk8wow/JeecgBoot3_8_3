-- `jeecg-boot`.newbie_brand definition

CREATE TABLE `newbie_brand` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '品牌名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除状态（0，正常，1已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品品牌';


-- `jeecg-boot`.newbie_category definition

CREATE TABLE `newbie_category` (
  `id` varchar(32) NOT NULL COMMENT '分类id',
  `name` varchar(50) NOT NULL COMMENT '分类名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品分类';


-- `jeecg-boot`.newbie_order definition

CREATE TABLE `newbie_order` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `order_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单号',
  `req_id` varchar(32) NOT NULL COMMENT '关联相应请求的ID',
  `item_id` varchar(32) NOT NULL COMMENT '关联相应清单列表中记录的ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除状态（0:正常，1:已删除）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order` (`order_no`),
  KEY `idx_req_id` (`req_id`),
  KEY `idx_item_id` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='新手村-订单表';


-- `jeecg-boot`.newbie_order_number definition

CREATE TABLE `newbie_order_number` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `order_date` varchar(10) NOT NULL COMMENT '生成订单的日期，yyyyMMdd, 例：20251111。',
  `seq` int NOT NULL COMMENT '四位序数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除状态（0:正常，1:已删除）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_number` (`order_date`,`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='新手村-订单号';


-- `jeecg-boot`.newbie_product definition

CREATE TABLE `newbie_product` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品名称',
  `brand_id` varchar(32) NOT NULL COMMENT '品牌ID',
  `category_id` varchar(32) NOT NULL COMMENT '分类id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='产品表';


-- `jeecg-boot`.newbie_request_process_number definition

CREATE TABLE `newbie_request_process_number` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `req_dept` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '申请部门',
  `req_date` varchar(50) NOT NULL COMMENT '申请日期，yyyyMMdd, 例：20251111',
  `seq` int NOT NULL COMMENT '三位序数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除状态（0:正常，1:已删除）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_process_number` (`req_dept`,`req_date`,`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='新手村-申请单-流程号';


-- `jeecg-boot`.newbie_request_todo definition

CREATE TABLE `newbie_request_todo` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `req_id` varchar(32) NOT NULL COMMENT '关联相应待办请求的ID',
  `category` tinyint NOT NULL COMMENT '1: 样品领用申请单， 2: ... ',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除状态（0:正常，1:已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='新手村-申请单-待办事项';


-- `jeecg-boot`.newbie_sample_items definition

CREATE TABLE `newbie_sample_items` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `req_id` varchar(32) NOT NULL COMMENT 'newbie_sample_request id',
  `ordinal` int NOT NULL COMMENT '序数',
  `brand_id` varchar(32) NOT NULL COMMENT '品牌ID',
  `category_id` varchar(32) NOT NULL COMMENT '类别ID',
  `product_id` varchar(32) NOT NULL COMMENT '产品ID',
  `qty_box` int NOT NULL COMMENT '箱数',
  `qty_order` int NOT NULL COMMENT '张数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除状态（0:正常，1:已删除）',
  PRIMARY KEY (`id`),
  KEY `idx_req_id` (`req_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='样品领用申请单-清单表';


-- `jeecg-boot`.newbie_sample_request definition

CREATE TABLE `newbie_sample_request` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `requester` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '申请人',
  `req_dept` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '申请部门',
  `req_date` timestamp NOT NULL COMMENT '申请日期',
  `proc_no` varchar(113) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '流程编号',
  `item_category` tinyint NOT NULL COMMENT '领用类型。1:样品',
  `phone` varchar(50) NOT NULL COMMENT '联系电话',
  `file_upload_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件URL',
  `person_in_charge_id` varchar(32) NOT NULL COMMENT '领用部门负责人的用户ID',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '审批状态。0:待办, 1:通过, 2:拒绝',
  `comment` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '评论',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除状态（0:正常，1:已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='样品领用申请单';

