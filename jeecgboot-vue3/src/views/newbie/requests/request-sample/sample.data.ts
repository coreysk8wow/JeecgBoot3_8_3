import { FormSchema } from "/@/components/Table";

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


export const productFormSchemas: FormSchema[] = [
	{
		//标题名称
		label: "jjj",
		//字段
		field: "name",
		//组件 支持组件详见 components/Form/src/types/index.ts 中的 ComponentType
		component: "JAddInput",
		/* componentProps: {
            rowKey: "id",
            labelKey: "realname", 

            //是否显示选择按钮
			showButton: true,
			//选择框标题
			modalTitle: "选择人员",
		}, */
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
