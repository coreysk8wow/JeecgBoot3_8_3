import { FormSchema } from "/@/components/Table";
import { ref } from "vue";
import { message } from "ant-design-vue";

//自定义表单字段
export const selectUserFormSchemas: FormSchema[] = [
	{
		//标题名称
		label: "领用部门负责人审核",
		//字段
		field: "name",
		//组件 支持组件详见 components/Form/src/types/index.ts 中的 ComponentType
		component: "JSelectUserByDept",
		componentProps: {
            rowKey: "id",
            labelKey: "realname", 

            //是否显示选择按钮
			showButton: true,
			//选择框标题
			modalTitle: "选择人员",
		},
		//标题宽度,支持数字和字符串
		labelWidth: 160,
		//标题长度，超过位数隐藏
		labelLength: 30,
		//一列占比总共24，比如一行显示2列
		colProps: { span: 30 },
		// 红星表示必填
		required: true,
	},
];

//自定义表单字段
export const uploadFileFormSchemas: FormSchema[] = [
	{
		//标题名称
		label: "附件上传",
		//字段
		field: "file",
		//组件 支持组件详见 components/Form/src/types/index.ts 中的 ComponentType
		component: "JUpload",
		componentProps: {
			//上传接口
			action: "http://localhost:8080/newbie/file/uploadFile",
			//文件类型限制
			accept: ".doc, .docx, .xls, .xlsx,",
			//最大文件大小10MB
			maxSize: 10 * 1024 * 1024,
            maxCount: 1, 
            beforeUpload: beforeUpload,
		},
		//标题宽度,支持数字和字符串
		labelWidth: 160,
		//标题长度，超过位数隐藏
		labelLength: 30,
		//一列占比总共24，比如一行显示2列
		colProps: { span: 30 },
		// 红星表示必填
		required: false, 
	},
];

export const columns = [
    {
        title: '序号',
        dataIndex: 'ordinal',
        width: '5%',
        align: 'center',
    },
    {
        title: '品牌',
        dataIndex: 'brand',
        align: 'center',
        width: '20%',
    },
    {
        title: '类别',
        dataIndex: 'category',
        align: 'center',
        width: '20%',
    },
    {
        title: '产品名称',
        dataIndex: 'product',
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
    {
        title: '操作',
        dataIndex: 'operation',
        width: '5%',
        align: 'center',
    },

];

export const itemCategoryOptions = ref([
    { label: '样品', value: 1 }
]);

// Brand options for dropdown
export const brandOptions = ref([
    // { label: '青岛啤酒', value: '1' },
    // { label: '青岛纯生', value: '2' },
    // { label: '雪花啤酒', value: '3' },
    // { label: '百威啤酒', value: '4' },
]);
export const categoryOptions = ref([
    // { label: '瓶', value: '1' },
    // { label: '罐', value: '2' },
    // { label: '桶', value: '3' },
]);
export const productOptions = ref([]
    // [
    // { label: '青岛啤酒500ml', value: '1' },
    // { label: '青岛纯生500ml', value: '2' },
    // { label: '雪花啤酒500ml', value: '3' },
    // { label: '百威啤酒500ml', value: '4' },
    // { label: '青岛啤酒经典易拉罐', value: '5' },
// ]
);


function beforeUpload(file) {
	const isLt10M = file.size / 1024 / 1024 < 10;
	if (!isLt10M) {
		message.error("附件大小不能超过10M!");
		return false;
	}
	return true;
};