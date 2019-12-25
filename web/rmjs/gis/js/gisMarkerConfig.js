var getMakerImg = {
	/** * 综合展现 ** */
	'01' : {// 卡口
		'01' : {// 国界卡口
			'01' : modelPath + 'images/markers/tollgates/nation_normal.png',
			'02' : modelPath + 'images/markers/tollgates/nation_unnormal.png',
			'03' : modelPath + 'images/markers/tollgates/nation_unnormal.png'
		},
		'02' : {// 省际卡口
			'01' : modelPath + 'images/markers/tollgates/province_normal.png',
			'02' : modelPath + 'images/markers/tollgates/province_unnormal.png',
			'03' : modelPath + 'images/markers/tollgates/province_unnormal.png'
		},
		'03' : {// 市际卡口
			'01' : modelPath + 'images/markers/tollgates/city_normal.png',
			'02' : modelPath + 'images/markers/tollgates/city_unnormal.png',
			'03' : modelPath + 'images/markers/tollgates/city_unnormal.png                                                                                                  '
		},
		'04' : {// 县际卡口
			'01' : modelPath + 'images/markers/tollgates/country_normal.png',
			'02' : modelPath + 'images/markers/tollgates/country_unnormal.png',
			'03' : modelPath + 'images/markers/tollgates/country_unnormal.png'
		},
		'05' : {// 公路主线卡口
			'01' : modelPath + 'images/markers/tollgates/mainline_normal.png',
			'02' : modelPath + 'images/markers/tollgates/mainline_unnormal.png',
			'03' : modelPath + 'images/markers/tollgates/mainline_unnormal.png'
		},
		'06' : {// 公路收费站卡口
			'01' : modelPath + 'images/markers/tollgates/toll-gate_normal.png',
			'02' : modelPath + 'images/markers/tollgates/toll-gate_unnormal.png',
			'03' : modelPath + 'images/markers/tollgates/toll-gate_unnormal.png'
		},
		'07' : {// 城区道路卡口
			'01' : modelPath + 'images/markers/tollgates/districtRoad_normal.png',
			'02' : modelPath + 'images/markers/tollgates/districtRoad_unnormal.png',
			'03' : modelPath + 'images/markers/tollgates/districtRoad_unnormal.png'
		},
		'08' : {// 城区路口卡口
			'01' : modelPath + 'images/markers/tollgates/roadIntersection_normal.png',
			'02' : modelPath + 'images/markers/tollgates/roadIntersection_unnormal.png',
			'03' : modelPath + 'images/markers/tollgates/roadIntersection_unnormal.png'
		},
		'default' : modelPath + 'images/markers/m1.png',
		'sheng' : modelPath + 'images/markers/m2.png',
		'cluster' : modelPath + 'images/markers/conv30.png',
		'onlyone' : modelPath + 'images/markers/tollgates/tollgate.png'
	},
	'02' : {// 违法取证设备
		'1' : modelPath + 'images/markers/wfqzsb.png', // 闯红灯自动记录设备
		'2' : modelPath + 'images/markers/wfqzsb.png', // 公路车辆智能监控设备
		'3' : modelPath + 'images/markers/wfqzsb.png', // 测速设备
		'4' : modelPath + 'images/markers/wfqzsb.png', // 闭路电视
		'5' : modelPath + 'images/markers/wfqzsb.png', // 移动摄像
		'6' : modelPath + 'images/markers/wfqzsb.png', // 数码相机
		'7' : modelPath + 'images/markers/wfqzsb.png', // 城区道路卡口
		'8' : modelPath + 'images/markers/wfqzsb.png', // 卫星定位装置
		'9' : modelPath + 'images/markers/wfqzsb.png', // 其他电子设备
		'default' : modelPath + 'images/markers/m1.png',
		'sheng' : modelPath + 'images/markers/m2.png',
		'cluster' : modelPath + 'images/markers/conv30.png',
		'onlyone' : modelPath + 'images/markers/wfqzsb.png'
	},
	'03' : {// 视频
		'1' : modelPath + 'images/markers/camera_normal.png', // 正常
		'2' : modelPath + 'images/markers/camera_unnormal.png', // 异常
		'default' : modelPath + 'images/markers/m1.png',
		'sheng' : modelPath + 'images/markers/m2.png',
		'cluster' : modelPath + 'images/markers/conv30.png',
		'onlyone' : modelPath + 'images/markers/camera_normal.png'
	},
	'04' : {// 执法服务站
		'1' : modelPath + 'images/markers/serviceStation/zffwz_province.png', // 省际
		'2' : modelPath + 'images/markers/serviceStation/zffwz_city.png', // 市际
		'3' : modelPath + 'images/markers/serviceStation/zffwz_country.png', // 县际
		'4' : modelPath + 'images/markers/serviceStation/zffwz_highway_exit.png', // 高速出入口
		'5' : modelPath + 'images/markers/serviceStation/zffwz_inout_city.png', // 进出城区
		'default' : modelPath + 'images/markers/m1.png',
		'sheng' : modelPath + 'images/markers/m2.png',
		'cluster' : modelPath + 'images/markers/conv30.png',
		'onlyone' : modelPath + 'images/markers/serviceStation/zffwz.png'
	},
	'05' : {// 停车场
		'1' : modelPath + 'images/markers/carpark.png', // 公共停车场
		'2' : modelPath + 'images/markers/carpark.png', // 专用停车场
		'default' : modelPath + 'images/markers/m1.png',
		'sheng' : modelPath + 'images/markers/m2.png',
		'cluster' : modelPath + 'images/markers/conv30.png',
		'onlyone' : modelPath + 'images/markers/carpark.png'
	},
	'06' : {// 气象检测设备
		'A' : modelPath + 'images/markers/qxjcsb.png', // 大雾
		//'B' : modelPath + 'images/markers/qxjcsb.png', // 冰冻
		//'C' : modelPath + 'images/markers/qxjcsb.png', // 横风
		'D' : modelPath + 'images/markers/qxjcsb.png', // 降雪
		'E' : modelPath + 'images/markers/qxjcsb.png', // 降雨
		//'F' : modelPath + 'images/markers/qxjcsb.png', // 高温
		'G' : modelPath + 'images/markers/qxjcsb.png', // 大风
		'J' : modelPath + 'images/markers/qxjcsb.png', // 温度异常
		'B,C,D,E,F' : modelPath + 'images/markers/qxjcsb.png',
		'default' : modelPath + 'images/markers/m1.png',
		'sheng' : modelPath + 'images/markers/m2.png',
		'cluster' : modelPath + 'images/markers/conv30.png',
		'onlyone' : modelPath + 'images/markers/qxjcsb.png'
	},
	'07' : {// 流量检测设备
		'A' : modelPath + 'images/markers/lljcsb.png', // 环形线圈检测器
		'B' : modelPath + 'images/markers/lljcsb.png', // 地磁检测器
		'C' : modelPath + 'images/markers/lljcsb.png', // 微波检测器
		'D' : modelPath + 'images/markers/lljcsb.png', // 超声波检测器
		'E' : modelPath + 'images/markers/lljcsb.png', // 红外检测器
		'F' : modelPath + 'images/markers/lljcsb.png', // 视频检测器
		'H' : modelPath + 'images/markers/lljcsb.png', // RFID阅读器
		'Z' : modelPath + 'images/markers/lljcsb.png', // 其他检测设备
		'default' : modelPath + 'images/markers/m1.png',
		'sheng' : modelPath + 'images/markers/m2.png',
		'cluster' : modelPath + 'images/markers/conv30.png',
		'onlyone' : modelPath + 'images/markers/lljcsb.png'
	},
	'08' : {// 可变信息标志牌
		'default' : modelPath + 'images/markers/m1.png',
		'sheng' : modelPath + 'images/markers/m2.png',
		'cluster' : modelPath + 'images/markers/conv30.png',
		'onlyone' : modelPath + 'images/markers/kbxxbzp.png'
	},
	'09' : {// 信号控制
		'default' : modelPath + 'images/markers/m1.png',
		'sheng' : modelPath + 'images/markers/m2.png',
		'cluster' : modelPath + 'images/markers/conv30.png',
		'onlyone' : modelPath + 'images/markers/xhkz.png'
	},
	'11' : { // 交通部门
		'default' : modelPath + 'images/markers/m1.png',
		'sheng' : modelPath + 'images/markers/m2.png',
		'cluster' : modelPath + 'images/markers/conv30.png',
		'onlyone' : modelPath + 'images/markers/jtbm.png'
	},
	'12' : { // 消防部门
		'default' : modelPath + 'images/markers/m1.png',
		'sheng' : modelPath + 'images/markers/m2.png',
		'cluster' : modelPath + 'images/markers/conv30.png',
		'onlyone' : modelPath + 'images/markers/xfbm.png'
	},
	'13' : {// 医院
		'default' : modelPath + 'images/markers/m1.png',
		'sheng' : modelPath + 'images/markers/m2.png',
		'cluster' : modelPath + 'images/markers/conv30.png',
		'onlyone' : modelPath + 'images/markers/hospital.png'
	},
	'14' : {// 修理厂
		'1' : modelPath + 'images/markers/repare.png', // 一类汽车维修
		'2' : modelPath + 'images/markers/repare.png', // 二类汽车维修
		'3' : modelPath + 'images/markers/repare.png', // 三类汽车维修
		'default' : modelPath + 'images/markers/m1.png',
		'sheng' : modelPath + 'images/markers/m2.png',
		'cluster' : modelPath + 'images/markers/conv30.png',
		'onlyone' : modelPath + 'images/markers/repare.png'
	},
	'15' : {//广播设备 无图标
		'default' : modelPath + 'images/markers/m1.png',
		'sheng' : modelPath + 'images/markers/m2.png',
		'cluster' : modelPath + 'images/markers/conv30.png',
		'onlyone' : modelPath + 'images/markers/gbsb.png'
	},
	'16' : {// 交警队
		'1' : modelPath + 'images/markers/trafficPoliceForce/buju.png', // 部局
		'2' : modelPath + 'images/markers/trafficPoliceForce/zongdui.png', // 交警总队
		'3' : modelPath + 'images/markers/trafficPoliceForce/zhidui.png', // 交警支队
		'4' : modelPath + 'images/markers/trafficPoliceForce/dadui.png', //交警大队 
		'5' : modelPath + 'images/markers/trafficPoliceForce/zhongdui.png', //交警中队 
		'default' : modelPath + 'images/markers/m1.png',
		'sheng' : modelPath + 'images/markers/m2.png',
		'cluster' : modelPath + 'images/markers/conv30.png',
		'onlyone' : modelPath + 'images/markers/trafficPoliceForce/zhidui.png'
	},
	/** * 阻断信息 ** */
	'1' : modelPath + 'images/markers/dlsg.png',
	'A1' : {// 道路流量
		'1' : modelPath + 'images/markers/flowIcon/green.png',
		'2' : modelPath + 'images/markers/flowIcon/yellow.png',
		'3' : modelPath + 'images/markers/flowIcon/red.png',
		'default' : modelPath + 'images/markers/m1.png',
		'sheng' : modelPath + 'images/markers/m2.png'
	},
	'A2' : {// 道路施工
		'onlyone' : modelPath + 'images/markers/dlsg.png',
		'default' : modelPath + 'images/markers/m1.png',
		'sheng' : modelPath + 'images/markers/m2.png'
	},
	'A3' : {// 交通施工
		'onlyone' : modelPath + 'images/markers/jtsg.png',
		'default' : modelPath + 'images/markers/m1.png',
		'sheng' : modelPath + 'images/markers/m2.png'
	},
	'A4' : {// 恶劣天气
		'onlyone' : modelPath + 'images/markers/eltq.png',
		'default' : modelPath + 'images/markers/m1.png',
		'sheng' : modelPath + 'images/markers/m2.png'
	},
	'A5' : {// 其他阻断
		'onlyone' : modelPath + 'images/markers/qtzdsj.png',
		'default' : modelPath + 'images/markers/m1.png',
		'sheng' : modelPath + 'images/markers/m2.png'
	},
	'A6' : {
		'onlyone' : modelPath + 'images/markers/qtzdsj.png',
		'default' : modelPath + 'images/markers/m1.png',
		'sheng' : modelPath + 'images/markers/m2.png'
	},
	'A7' : {
		'single' : modelPath + 'images/markers/qtzdsj.png',
		'default' : modelPath + 'images/markers/m1.png',
		'sheng' : modelPath + 'images/markers/m2.png'
	},
	'A8' : {
		'onlyone' : modelPath + 'images/markers/qtzdsj.png',
		'default' : modelPath + 'images/markers/m1.png',
		'sheng' : modelPath + 'images/markers/m2.png'
	},
	'A9' : {
		'onlyone' : modelPath + 'images/markers/qtzdsj.png',
		'default' : modelPath + 'images/markers/m1.png',
		'sheng' : modelPath + 'images/markers/m2.png'
	},
	'18' : {// 警车
		'onlyone' : modelPath + 'images/markers/police_car.png',
		'motorcycle':modelPath + 'images/markers/police_motor.png'//摩托车
	},
	'19' : {// 警察
		'onlyone' : modelPath + 'images/markers/police_man.png'
	},
	'20' : {//检测设备
		'onlyone' : modelPath + 'images/markers/spsjjcq.png'
	},
	'blues' : modelPath + 'images/markers/m1.png', // 蓝
	'yellows' : modelPath + 'images/markers/m2.png', // 黄
	'static3' : modelPath + 'images/markers/m3.png',
	'reds' : modelPath + 'images/markers/m4.png', // 红
	'greens' : modelPath + 'images/markers/m5.png', // 绿
	/** * 集成指挥 ** */
	'yjzh' : {
		'A' : { // 交通事故
			'no_handle' : {// 未处置
				'1' : modelPath + 'images/markers/emergency/no_handle/jtsg_1.png',
				'2' : modelPath + 'images/markers/emergency/no_handle/jtsg_2.png',
				'3' : modelPath + 'images/markers/emergency/no_handle/jtsg_3.png',
				'4' : modelPath + 'images/markers/emergency/no_handle/jtsg_4.png'
			},
			'no_feedback' : {// 未反馈
				'1' : modelPath + 'images/markers/emergency/no_feedback/jtsg_1.png',
				'2' : modelPath + 'images/markers/emergency/no_feedback/jtsg_2.png',
				'3' : modelPath + 'images/markers/emergency/no_feedback/jtsg_3.png',
				'4' : modelPath + 'images/markers/emergency/no_feedback/jtsg_4.png'
			}
		},
		'B' : { // 布控嫌疑车辆
			'no_handle' : {// 未处置
				'1' : modelPath + 'images/markers/emergency/no_handle/bkxycl_1.png',
				'2' : modelPath + 'images/markers/emergency/no_handle/bkxycl_2.png',
				'3' : modelPath + 'images/markers/emergency/no_handle/bkxycl_3.png',
				'4' : modelPath + 'images/markers/emergency/no_handle/bkxycl_4.png'
			},
			'no_feedback' : {// 未反馈
				'1' : modelPath + 'images/markers/emergency/no_feedback/bkxycl_1.png',
				'2' : modelPath + 'images/markers/emergency/no_feedback/bkxycl_2.png',
				'3' : modelPath + 'images/markers/emergency/no_feedback/bkxycl_3.png',
				'4' : modelPath + 'images/markers/emergency/no_feedback/bkxycl_4.png'
			}
		},
		'C' : { // 恶劣天气
			'no_handle' : {// 未处置
				'1' : modelPath + 'images/markers/emergency/no_handle/eltq_1.png',
				'2' : modelPath + 'images/markers/emergency/no_handle/eltq_2.png',
				'3' : modelPath + 'images/markers/emergency/no_handle/eltq_3.png',
				'4' : modelPath + 'images/markers/emergency/no_handle/eltq_4.png'
			},
			'no_feedback' : {// 未反馈
				'1' : modelPath + 'images/markers/emergency/no_feedback/eltq_1.png',
				'2' : modelPath + 'images/markers/emergency/no_feedback/eltq_2.png',
				'3' : modelPath + 'images/markers/emergency/no_feedback/eltq_3.png',
				'4' : modelPath + 'images/markers/emergency/no_feedback/eltq_4.png'
			}
		},
		'D' : { // 地质灾害
			'no_handle' : {// 未处置
				'1' : modelPath + 'images/markers/emergency/no_handle/dzzh_1.png',
				'2' : modelPath + 'images/markers/emergency/no_handle/dzzh_1.png',
				'3' : modelPath + 'images/markers/emergency/no_handle/dzzh_3.png',
				'4' : modelPath + 'images/markers/emergency/no_handle/dzzh_4.png'
			},
			'no_feedback' : {// 未反馈
				'1' : modelPath + 'images/markers/emergency/no_feedback/dzzh_1.png',
				'2' : modelPath + 'images/markers/emergency/no_feedback/dzzh_1.png',
				'3' : modelPath + 'images/markers/emergency/no_feedback/dzzh_3.png',
				'4' : modelPath + 'images/markers/emergency/no_feedback/dzzh_4.png'
			}
		},
		'E' : { // 交通流量大
			'no_handle' : {// 未处置
				'1' : modelPath + 'images/markers/emergency/no_handle/jtll_1.png',
				'2' : modelPath + 'images/markers/emergency/no_handle/jtll_2.png',
				'3' : modelPath + 'images/markers/emergency/no_handle/jtll_3.png',
				'4' : modelPath + 'images/markers/emergency/no_handle/jtll_4.png'
			},
			'no_feedback' : {// 未反馈
				'1' : modelPath + 'images/markers/emergency/no_feedback/jtll_1.png',
				'2' : modelPath + 'images/markers/emergency/no_feedback/jtll_2.png',
				'3' : modelPath + 'images/markers/emergency/no_feedback/jtll_3.png',
				'4' : modelPath + 'images/markers/emergency/no_feedback/jtll_4.png'
			}
		},
		'F' : { // 车辆交通违法
			'no_handle' : {// 未处置
				'1' : modelPath + 'images/markers/emergency/no_handle/clnx_1.png',
				'2' : modelPath + 'images/markers/emergency/no_handle/clnx_2.png',
				'3' : modelPath + 'images/markers/emergency/no_handle/clnx_3.png',
				'4' : modelPath + 'images/markers/emergency/no_handle/clnx_4.png'
			},
			'no_feedback' : {// 未反馈
				'1' : modelPath + 'images/markers/emergency/no_feedback/clnx_1.png',
				'2' : modelPath + 'images/markers/emergency/no_feedback/clnx_2.png',
				'3' : modelPath + 'images/markers/emergency/no_feedback/clnx_3.png',
				'4' : modelPath + 'images/markers/emergency/no_feedback/clnx_4.png'
			}
		},
		'G' : { // 车辆停驶
			'no_handle' : {// 未处置
				'1' : modelPath + 'images/markers/emergency/no_handle/clts_1.png',
				'2' : modelPath + 'images/markers/emergency/no_handle/clts_2.png',
				'3' : modelPath + 'images/markers/emergency/no_handle/clts_3.png',
				'4' : modelPath + 'images/markers/emergency/no_handle/clts_4.png'
			},
			'no_feedback' : {// 未反馈
				'1' : modelPath + 'images/markers/emergency/no_feedback/clts_1.png',
				'2' : modelPath + 'images/markers/emergency/no_feedback/clts_2.png',
				'3' : modelPath + 'images/markers/emergency/no_feedback/clts_3.png',
				'4' : modelPath + 'images/markers/emergency/no_feedback/clts_4.png'
			}
		},
		'G' : { // 行人违法
			'no_handle' : {// 未处置
				'1' : modelPath + 'images/markers/emergency/no_handle/xrwf_1.png',
				'2' : modelPath + 'images/markers/emergency/no_handle/xrwf_2.png',
				'3' : modelPath + 'images/markers/emergency/no_handle/xrwf_3.png',
				'4' : modelPath + 'images/markers/emergency/no_handle/xrwf_4.png'
			},
			'no_feedback' : {// 未反馈
				'1' : modelPath + 'images/markers/emergency/no_feedback/xrwf_1.png',
				'2' : modelPath + 'images/markers/emergency/no_feedback/xrwf_2.png',
				'3' : modelPath + 'images/markers/emergency/no_feedback/xrwf_3.png',
				'4' : modelPath + 'images/markers/emergency/no_feedback/xrwf_4.png'
			}
		},
		'default' : modelPath + 'images/markers/emergency/alarm.gif'
	}
};