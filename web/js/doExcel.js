function PrintSqbOne(printer, m_hpzl, m_hphm, m_ywlx, m_ywyy, m_zxyy, m_clpp1,
		m_clxh, m_clsbdh, m_hdfs, m_syxz, m_syr, m_zzxxdz, m_yzbm1, m_lxdh,
		m_sjhm, m_dzyx, m_zcd_province, m_zcd_fzjg, m_veh_agentdwmc,
		m_veh_agentzsdz, m_veh_agentyzbm, m_veh_agentdwlxdh, m_veh_agentdzyx,
		m_veh_agentxm, m_veh_agentlxdh) {
	var printdata = "";

	printdata += "G4," + m_hpzl + ",0|";
	printdata += "U4," + m_hphm + ",0|";
	if (m_ywlx == "A") {
		printdata += "G5,,1|";
	} else if (m_ywlx == "G") {
		printdata += "M5,,1|";
	} else if (m_ywlx == "I") {
		printdata += "AA5,,1|";
	} else if (m_ywlx == "B"
			&& (m_ywyy.indexOf("A") > -1 || m_ywyy.indexOf("B") > -1)) {
		printdata += "G6,,1|";
	} else if (m_ywlx == "B" && m_ywyy.indexOf("C") > -1) {
		printdata += "M6,,1|";
	}
	if (m_ywlx == "G") {
		if (m_zxyy == "A") {
			printdata += "G7,,1|";
		} else if (m_zxyy == "B") {
			printdata += "H7,,1|";
		} else if (m_zxyy == "F") {
			printdata += "M7,,1|";
		} else if (m_zxyy == "C") {
			printdata += "U7,,1|";
		}
	}
	printdata += "G8," + m_clpp1 + ' ' + m_clxh + ",0|";
	printdata += "U8," + m_clsbdh + ",0|";
	switch (m_hdfs) {
	case "A":// 购买
		printdata += "G10,,1|";
		break;
	case "B":// 赠予
		printdata += "N10,,1|";
		break;
	case "C":// 继承
		printdata += "J10,,1|";
		break;
	case "D":// 法院调解
		printdata += "Y11,,1|";
		break;
	case "E":// 调拨
		printdata += "G11,,1|";
		break;
	case "F":// 协议抵偿债务
		printdata += "R10,,1|";
		break;
	case "G":// 军转
		// printdata+= "G10,,1|";
		break;
	case "H":// 仲裁裁决
		printdata += "R11,,1|";
		break;
	case "I":// 资产重组
		printdata += "H11,,1|";
		break;
	case "J":// 资产整体买卖
		printdata += "J11,,1|";
		break;
	case "K":// 法院裁定
		printdata += "AB11,,1|";
		break;
	case "L":// 法院判决
		printdata += "G12,,1|";
		break;
	case "M":// 境外自带
		printdata += "H10,,1|";
		break;
	case "N":// 中奖
		printdata += "AB10,,1|";
		break;
	case "O":// 协议离婚
		printdata += "Y10,,1|";
		break;
	case "Z":// 其他
		printdata += "H12,,1|";
		break;
	}

	switch (m_syxz) {
	case "A":// 非营运
		printdata += "G14,,1|";
		break;
	case "B":// 公路客运
		printdata += "H14,,1|";
		break;
	case "C":// 公交客运
		printdata += "J14,,1|";
		break;
	case "D":// 出租客运
		printdata += "O14,,1|";
		break;
	case "E":// 旅游客运
		printdata += "U14,,1|";
		break;
	case "F":// 货运
		printdata += "O15,,1|";
		break;
	case "G":// 租赁
		printdata += "AA14,,1|";
		break;
	case "H":// 警用
		printdata += "AB15,,1|";
		break;
	case "I":// 消防
		printdata += "G16,,1|";
		break;
	case "J":// 救护
		printdata += "H16,,1|";
		break;
	case "K":// 工程救险
		printdata += "J16,,1|";
		break;
	case "L":// 营转非
		printdata += "O16,,1|";
		break;
	case "M":// 出租转非
		printdata += "U16,,1|";
		break;
	case "N":// 教练
		printdata += "AB14,,1|";
		break;
	case "O":// 幼儿校车
		printdata += "G15,,1|";
		break;
	case "P":// 小学生校车
		printdata += "H15,,1|";
		break;
	case "Q":// 其他校车
		printdata += "J15,,1|";
		break;
	case "R":// 危化品运输
		printdata += "U15,,1|";
		break;

	}

	printdata += "G17," + m_syr + ",0|";
	printdata += "G18," + m_zzxxdz + ",0|";
	printdata += "G19," + m_yzbm1 + ",0|";
	printdata += "N19," + m_lxdh + ",0|";
	printdata += "G20," + m_dzyx + ",0|";
	printdata += "N20," + m_sjhm + ",0|";
	printdata += "H21," + m_zcd_province + ",0|";
	printdata += "G22," + m_zcd_fzjg + ",0|";
	printdata += "G23," + m_veh_agentdwmc + ",0|";
	printdata += "G25," + m_veh_agentzsdz + ",0|";
	printdata += "G26," + m_veh_agentyzbm + ",0|";
	printdata += "N26," + m_veh_agentdwlxdh + ",0|";
	printdata += "G27," + m_veh_agentdzyx + ",0|";
	printdata += "G28," + m_veh_agentxm + ",0|";
	printdata += "N28," + m_veh_agentlxdh + ",0|";
	printer.PrintSqb1(printdata, "sqb1");
}

function PrintSqbTwo(printer, m_hpzl, m_hphm, m_ywyy, m_zsxxdz, m_bghxx,
		m_syxz, m_syr, m_zzxxdz, m_yzbm1, m_lxdh, m_sjhm, m_dzyx,
		m_zcd_province, m_zcd_fzjg, m_veh_agentdwmc, m_veh_agentzsdz,
		m_veh_agentyzbm, m_veh_agentdwlxdh, m_veh_agentdzyx, m_veh_agentxm,
		m_veh_agentlxdh) {
	var printdata = "";

	printdata += "G4," + m_hpzl + ",0|";
	printdata += "Y4," + m_hphm + ",0|";
	if (m_ywyy.indexOf("N") > -1) {
		printdata += "C6,,1|";
		printdata += "G6," + m_syr + ",0|";
	}
	if (m_ywyy.indexOf("A") > -1) {
		printdata += "C7,,1|";
		printdata += "G7," + m_syr + ",0|";
	}
	if (m_ywyy.indexOf("O") > -1) {
		printdata += "C7,,1|";
		printdata += "G7," + m_zsxxdz + ",0|";
	}
	if (m_ywyy.indexOf("P") > -1) {
		printdata += "C9,,1|";
		printdata += "K9," + m_zzxxdz + ",0|";
		printdata += "K10," + m_yzbm1 + ",0|";
		printdata += "Z10," + m_lxdh + ",0|";
		printdata += "K11," + m_dzyx + ",0|";
		printdata += "Z11," + m_sjhm + ",0|";
	}
	if (m_ywyy.indexOf("J") > -1) {
		printdata += "C12,,1|";
		printdata += "I12," + m_zcd_province + ",0|";
		printdata += "T12," + m_zcd_fzjg + ",0|";
	}
	if (m_ywyy.indexOf("I") > -1) {
		printdata += "C13,,1|";
		switch (m_syxz) {
		case "B":// 公路客运
			printdata += "G14,,1|";
			break;
		case "C":// 公交客运
			printdata += "K14,,1|";
			break;
		case "D":// 出租客运
			printdata += "P14,,1|";
			break;
		case "E":// 旅游客运
			printdata += "U14,,1|";
			break;
		case "F":// 货运
			printdata += "AB14,,1|";
			break;
		case "G":// 租赁
			printdata += "Z14,,1|";
			break;
		case "H":// 警用
			printdata += "AB15,,1|";
			break;
		case "I":// 消防
			printdata += "AD16,,1|";
			break;
		case "J":// 救护
			printdata += "G16,,1|";
			break;
		case "K":// 工程救险
			printdata += "K16,,1|";
			break;
		case "L":// 营转非
			printdata += "P16,,1|";
			break;
		case "M":// 出租转非
			printdata += "U16,,1|";
			break;
		case "N":// 教练
			printdata += "AD14,,1|";
			break;
		case "O":// 幼儿校车
			printdata += "G15,,1|";
			break;
		case "P":// 小学生校车
			printdata += "K15,,1|";
			break;
		case "Q":// 其他校车
			printdata += "P15,,1|";
			break;
		case "R":// 危化品运输
			printdata += "U15,,1|";
			break;
		}
	}
	if (m_ywyy.indexOf("F") > -1) {
		printdata += "C18,,1|";
	}
	if (m_ywyy.indexOf("G") > -1) {
		printdata += "C20,,1|";
	}
	if (m_ywyy.indexOf("D") > -1) {
		printdata += "C21,,1|";
	}
	if (m_ywyy.indexOf("H") > -1) {
		printdata += "C22,,1|";
	}
	if (m_ywyy.indexOf("L") > -1) {
		printdata += "C24,,1|";
	}
	if (m_ywyy.indexOf("K") > -1) {
		printdata += "C25,,1|";
	}
	if (m_ywyy.indexOf("M") > -1) {
		printdata += "C26,,1|";
	}
	if (m_ywyy.indexOf("F") > -1 || m_ywyy.indexOf("G") > -1
			|| m_ywyy.indexOf("D") > -1 || m_ywyy.indexOf("H") > -1
			|| m_ywyy.indexOf("L") > -1 || m_ywyy.indexOf("K") > -1
			|| m_ywyy.indexOf("M") > -1) {
		printdata += "G19,变更后的信息：" + m_bghxx + ",0|";
	}

	printdata += "F27," + m_veh_agentdwmc + ",0|";
	printdata += "F29," + m_veh_agentzsdz + ",0|";
	printdata += "F30," + m_veh_agentyzbm + ",0|";
	printdata += "P30," + m_veh_agentdwlxdh + ",0|";
	printdata += "F31," + m_veh_agentdzyx + ",0|";
	printdata += "F32," + m_veh_agentxm + ",0|";
	printdata += "P32," + m_veh_agentlxdh + ",0|";
	printer.PrintSqb1(printdata, "sqb2");
}

function PrintSqbThree(printer, m_hpzl, m_hphm, m_ywyy, m_syr, m_veh_agentdwmc,
		m_veh_agentzsdz, m_veh_agentyzbm, m_veh_agentdwlxdh, m_veh_agentdzyx,
		m_veh_agentxm, m_veh_agentlxdh, m_dyqr_xm, m_dyqr_xxdz, m_dyqr_yzbm,
		m_dyqr_lxdh, m_dyqr_dzyx, m_impawn_agentdwmc, m_impawn_agentzsdz,
		m_impawn_agentyzbm, m_impawn_agentdwlxdh, m_impawn_agentdzyx,
		m_impawn_agentxm, m_impawn_agentlxdh) {
	var printdata = "";
	printdata += "F4," + m_hpzl + ",0|";
	printdata += "Z4," + m_hphm + ",0|";
	if (m_ywyy.indexOf("A")>-1) {
		printdata += "F6,,1|";
	} else if (m_ywyy.indexOf("B")>-1) {
		printdata += "G6,,1|";
	} else if (m_ywyy.indexOf("C")>-1) {
		printdata += "N6,,1|";
	} else if (m_ywyy.indexOf("D")>-1) {
		printdata += "T6,,1|";
	}
	printdata += "F7," + m_syr + ",0|";
	printdata += "F8," + m_veh_agentdwmc + ",0|";
	printdata += "F10," + m_veh_agentzsdz + ",0|";
	printdata += "F12," + m_veh_agentyzbm + ",0|";
	printdata += "L12," + m_veh_agentdwlxdh + ",0|";
	printdata += "F13," + m_veh_agentdzyx + ",0|";
	printdata += "F14," + m_veh_agentxm + ",0|";
	printdata += "L14," + m_veh_agentlxdh + ",0|";

	printdata += "F15," + m_dyqr_xm + ",0|";
	printdata += "F17," + m_dyqr_xxdz + ",0|";
	printdata += "F18," + m_dyqr_yzbm + ",0|";
	printdata += "L18," + m_dyqr_lxdh + ",0|";
	printdata += "F19," + m_dyqr_dzyx + ",0|";

	printdata += "F20," + m_impawn_agentdwmc + ",0|";
	printdata += "F22," + m_impawn_agentzsdz + ",0|";
	printdata += "F24," + m_impawn_agentyzbm + ",0|";
	printdata += "L24," + m_impawn_agentdwlxdh + ",0|";
	printdata += "F25," + m_impawn_agentdzyx + ",0|";
	printdata += "F26," + m_impawn_agentxm + ",0|";
	printdata += "L26," + m_impawn_agentlxdh + ",0|";
	printer.PrintSqb1(printdata, "sqb3");
}

function PrintSqbFour(printer, m_hpzl, m_hphm, m_ywlx, m_ywyy, m_sqyy, m_syr, m_veh_zsxxdz,
		m_veh_yzbm, m_veh_lxdh, m_veh_dzyx, m_veh_sjhm, m_agentsyr,
		m_agentzsdz, m_agentyzbm, m_agentdwlxdh,
		m_agentdzyx, m_agentxm, m_agentlxdh) {
		
	
	var printdata = "";


	printdata += "G4," + m_hpzl + ",0|";
	printdata += "Z4," + m_hphm + ",0|";

	if (m_ywlx=="K") {

		if (m_ywyy=="A") {
			printdata += "E6,,1|";

			if (m_sqyy=="A") {

				printdata += "H6,,1|";

			}else if (m_sqyy=="B") {

				printdata += "N6,,1|";

			}else if (m_sqyy=="C") {

				printdata += "V6,,1|";

			}

		}

		else if (m_ywyy=="D") {
			printdata += "E7,,1|";
			printdata += "H7,,1|";
		}

		else if (m_ywyy=="B") {

			printdata += "E8,,1|";

			if (m_sqyy=="A") {

				printdata += "H8,,1|";

			}else if (m_sqyy=="B") {

				printdata += "V8,,1|";

			}
		}else if (m_ywyy=="G") {
			printdata += "E15,,1|";

		}else if (m_ywyy=="H") {

			printdata += "E14,,1|";

			if (m_sqyy=="A") {

				printdata += "H14,,1|";

			}else if (m_sqyy=="B") {

				printdata += "V14,,1|";

			}
		}

	}else if (m_ywlx=="P") {
		printdata += "E13,,1|";
		if (m_ywyy=="A") {
			
			printdata += "H13,,1|";
          }

	}else if (m_ywlx=="R") {

		if (m_ywyy=="A") {
		
			printdata += "V13,,1|";

		}

	}else if (m_ywlx=="L") {//登记证书

		if (m_ywyy=="I") {
			printdata += "E10,,1|";

		}else if (m_ywyy=="C") {

			printdata += "E11,,1|";

			if (m_sqyy=="A") {

				printdata += "N11,,1|";

			}else if (m_sqyy=="B") {

				printdata += "V11,,1|";

			}
		}else if (m_ywyy=="F") {
			printdata += "E12,,1|";

		}

	}

	printdata += "G16," + m_syr + ",0|";
	printdata += "G17," + m_veh_zsxxdz + ",0|";
	printdata += "G19," + m_veh_yzbm + ",0|";
	printdata += "R19," + m_veh_lxdh + ",0|";
	printdata += "G21," + m_veh_dzyx + ",0|";
	printdata += "R21," + m_veh_sjhm + ",0|";

	printdata += "G23," + m_agentsyr + ",0|";
	printdata += "G24," + m_agentzsdz + ",0|";
	printdata += "G25," + m_agentyzbm + ",0|";
	printdata += "R25," + m_agentdwlxdh + ",0|";
	printdata += "G26," + m_agentdzyx + ",0|";
	printdata += "G27," + m_agentxm + ",0|";
	printdata += "R27," + m_agentlxdh + ",0|";

	printer.PrintSqb1(printdata, "sqb4");
}