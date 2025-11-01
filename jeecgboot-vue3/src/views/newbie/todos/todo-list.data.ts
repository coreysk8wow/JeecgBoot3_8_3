import { BasicColumn } from "/@/components/Table/src/types/table";

export const todoListColumns: BasicColumn[] = [
    {
        title: '请求ID',
        dataIndex: 'reqId',
        ifShow: false,
    },
    {
        title: '请求标题',
        dataIndex: 'reqTitle',
        align: 'center',
        width: '20%',
    },
    {
        title: '申请人',
        dataIndex: 'requester',
        align: 'center',
        width: '15%',
    },
    {
        title: '申请部门',
        dataIndex: 'reqDept',
        width: '15%',
        align: 'center',
    },
    {
        title: '申请日期',
        dataIndex: 'reqDate',
        width: '20%',
        align: 'center',
    },
    {
        title: '状态',
        dataIndex: 'statusName',
        width: '15%',
        align: 'center',
    },
];

export const todoDetailsColumns: BasicColumn[] = [
    {
        title: '请求标题',
        dataIndex: 'reqTitle',
        align: 'center',
        width: '20%',
    },
    {
        title: '申请人',
        dataIndex: 'requester',
        align: 'center',
        width: '15%',
    },
    {
        title: '申请部门',
        dataIndex: 'reqDept',
        width: '15%',
        align: 'center',
    },
    {
        title: '申请日期',
        dataIndex: 'reqDate',
        width: '20%',
        align: 'center',
    },
    {
        title: '状态',
        dataIndex: 'status',
        width: '15%',
        align: 'center',
    },
];


export const requestSampleItemsColumns = [
    {
        title: '序号',
        dataIndex: 'ordinal',
        width: '5%',
        align: 'center',
    },
    {
        title: '品牌',
        dataIndex: 'brandName',
        align: 'center',
        width: '20%',
    },
    {
        title: '类别',
        dataIndex: 'categoryName',
        align: 'center',
        width: '20%',
    },
    {
        title: '产品名称',
        dataIndex: 'productName',
        width: '30%',
        align: 'center',
    },
    {
        title: '箱数',
        dataIndex: 'qtyBox',
        width: '10%',
        align: 'center',
    },
    {
        title: '张数',
        dataIndex: 'qtyOrder',
        width: '10%',
        align: 'center',
    },

];