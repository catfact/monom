MonoM {

	classvar seroscnet, discovery, devicelst;
	var <>prefix, <>rot, oscout;

	*new
		{ arg prefix, rot;

	devicelst = List.new(0);
	seroscnet = NetAddr.new("localhost", 12002);
	discovery = OSCdef.newMatching(\monomediscover,
			{|msg, time, addr, recvPort|
			Post << "Out2SerialOSCPort = " << msg[3] << Char.nl;
			devicelst.add(msg[3]);
		    }, '/serialosc/device', seroscnet);

	seroscnet.sendMsg("/serialosc/list", "127.0.0.1", NetAddr.localAddr.port);

	^super.new.init
		(prefix, rot);

	}

	init { arg prefix_, rot_;

		prefix = prefix_;
		rot = rot_;

	}

// See here: http://monome.org/docs/tech:osc
// if you need further explanation of the led methods below
useDevice { arg devicenum;

		oscout = NetAddr.new("localhost", devicelst[devicenum].value);
		Post << "Using device on Port# " << devicelst[devicenum].value << Char.nl;

		oscout.sendMsg("/sys/port", NetAddr.localAddr.port);
		oscout.sendMsg("/sys/prefix", prefix);
		oscout.sendMsg("/sys/rotation", rot);
	}

usePort { arg portnum;

		oscout = NetAddr.new("localhost", portnum);
		Post << "Using device on Port# " << portnum << Char.nl;

		oscout.sendMsg("/sys/port", NetAddr.localAddr.port);
		oscout.sendMsg("/sys/prefix", prefix);
		oscout.sendMsg("/sys/rotation", rot);
	}

ledset	{ arg col, row, noff;
		oscout.sendMsg(prefix++"/grid/led/set", col, row, noff);
	}

ledall 	{ arg noff;
		oscout.sendMsg(prefix++"/grid/led/all", noff);
	}

ledmap	{ arg exoff, whyoff, larr;
		var bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8;
		for (0, 7, {arg i;
					case {i == 0} {bt1 = larr[i]}
						{i == 1} {bt2 = larr[i]}
						{i == 2} {bt3 = larr[i]}
						{i == 3} {bt4 = larr[i]}
						{i == 4} {bt5 = larr[i]}
						{i == 5} {bt6 = larr[i]}
						{i == 6} {bt7 = larr[i]}
						{i == 7} {bt8 = larr[i]};
				  }
			);
		oscout.sendMsg(prefix++"/grid/led/map", exoff, whyoff,
										bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8);
	}

ledrow	{ arg exoff, why, bit1, bit2;
		oscout.sendMsg(prefix++"/grid/led/row", exoff, why, bit1, bit2);
	}

ledcol	{ arg ex, whyoff, bit1, bit2;
		oscout.sendMsg(prefix++"/grid/led/col", ex, whyoff, bit1, bit2);
	}

intensity	{ arg shitisintensyo;
		oscout.sendMsg(prefix++"/grid/led/intensity", shitisintensyo);
	}

levset	{ arg col, row, lev;
		oscout.sendMsg(prefix++"/grid/led/level/set", col, row, lev);
	}

levall	{ arg lev;
		oscout.sendMsg(prefix++"/grid/led/level/all", lev);
	}

levmap	{ arg exoff, whyoff, larr;
		var l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16,
		l17, l18, l19, l20, l21, l22, l23, l24, l25, l26, l27, l28, l29, l30, l31, l32,
		l33, l34, l35, l36, l37, l38, l39, l40, l41, l42, l43, l44, l45, l46, l47, l48,
		l49, l50, l51, l52, l53, l54, l55, l56, l57, l58, l59, l60, l61, l62, l63, l64;

		for (0, 63, {arg i;
					case {i == 0} {l1 = larr[i]}
						{i == 1} {l2 = larr[i]}
						{i == 2} {l3 = larr[i]}
						{i == 3} {l4 = larr[i]}
						{i == 4} {l5 = larr[i]}
						{i == 5} {l6 = larr[i]}
						{i == 6} {l7 = larr[i]}
						{i == 7} {l8 = larr[i]}
						{i == 8} {l9 = larr[i]}
						{i == 9} {l10 = larr[i]}
						{i == 10} {l11 = larr[i]}
						{i == 11} {l12 = larr[i]}
						{i == 12} {l13 = larr[i]}
						{i == 13} {l14 = larr[i]}
						{i == 14} {l15 = larr[i]}
						{i == 15} {l16 = larr[i]}
						{i == 16} {l17 = larr[i]}
						{i == 17} {l18 = larr[i]}
						{i == 18} {l19 = larr[i]}
						{i == 19} {l20 = larr[i]}
						{i == 20} {l21 = larr[i]}	    //<- highly inefficient long 'case' but�
						{i == 21} {l22 = larr[i]}    //computers are fast enough not to notice :p
						{i == 22} {l23 = larr[i]}
						{i == 23} {l24 = larr[i]}
						{i == 24} {l25 = larr[i]}
						{i == 25} {l26 = larr[i]}
						{i == 26} {l27 = larr[i]}
						{i == 27} {l28 = larr[i]}
						{i == 28} {l29 = larr[i]}
						{i == 29} {l30 = larr[i]}
						{i == 30} {l31 = larr[i]}
						{i == 31} {l32 = larr[i]}
						{i == 32} {l33 = larr[i]}
						{i == 33} {l34 = larr[i]}
						{i == 34} {l35 = larr[i]}
						{i == 35} {l36 = larr[i]}
						{i == 36} {l37 = larr[i]}
						{i == 37} {l38 = larr[i]}
						{i == 38} {l39 = larr[i]}
						{i == 39} {l40 = larr[i]}
						{i == 40} {l41 = larr[i]}
						{i == 41} {l42 = larr[i]}
						{i == 42} {l43 = larr[i]}
						{i == 43} {l44 = larr[i]}
						{i == 44} {l45 = larr[i]}
						{i == 45} {l46 = larr[i]}
						{i == 46} {l47 = larr[i]}
						{i == 47} {l48 = larr[i]}
						{i == 48} {l49 = larr[i]}
						{i == 49} {l50 = larr[i]}
						{i == 50} {l51 = larr[i]}
						{i == 51} {l52 = larr[i]}
						{i == 52} {l53 = larr[i]}
						{i == 53} {l54 = larr[i]}
						{i == 54} {l55 = larr[i]}
						{i == 55} {l56 = larr[i]}
						{i == 56} {l57 = larr[i]}
						{i == 57} {l58 = larr[i]}
						{i == 58} {l59 = larr[i]}
						{i == 59} {l60 = larr[i]}
						{i == 60} {l61 = larr[i]}
						{i == 61} {l62 = larr[i]}
						{i == 62} {l63 = larr[i]}
						{i == 63} {l64 = larr[i]};
				  }
			);

		oscout.sendMsg(prefix++"/grid/led/level/map", exoff, whyoff,
		l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16,
		l17, l18, l19, l20, l21, l22, l23, l24, l25, l26, l27, l28, l29, l30, l31, l32,
		l33, l34, l35, l36, l37, l38, l39, l40, l41, l42, l43, l44, l45, l46, l47, l48,
		l49, l50, l51, l52, l53, l54, l55, l56, l57, l58, l59, l60, l61, l62, l63, l64);

	}

levrow	{ arg exoff, why, larr;
		var lv1, lv2, lv3, lv4, lv5, lv6, lv7, lv8;
		for (0, 7, {arg i;
					case {i == 0} {lv1 = larr[i]}
						{i == 1} {lv2 = larr[i]}
						{i == 2} {lv3 = larr[i]}
						{i == 3} {lv4 = larr[i]}
						{i == 4} {lv5 = larr[i]}
						{i == 5} {lv6 = larr[i]}
						{i == 6} {lv7 = larr[i]}
						{i == 7} {lv8 = larr[i]};
				  }
			);
		oscout.sendMsg(prefix++"/grid/led/level/row", exoff, why,
							lv1, lv2, lv3, lv4, lv5, lv6, lv7, lv8);
	}

levcol	{ arg ex, whyoff, larr;
		var lv1, lv2, lv3, lv4, lv5, lv6, lv7, lv8;
		for (0, 7, {arg i;
					case {i == 0} {lv1 = larr[i]}
						{i == 1} {lv2 = larr[i]}
						{i == 2} {lv3 = larr[i]}
						{i == 3} {lv4 = larr[i]}
						{i == 4} {lv5 = larr[i]}
						{i == 5} {lv6 = larr[i]}
						{i == 6} {lv7 = larr[i]}
						{i == 7} {lv8 = larr[i]};
				  }
			);
		oscout.sendMsg(prefix++"/grid/led/level/col", ex, whyoff,
							lv1, lv2, lv3, lv4, lv5, lv6, lv7, lv8);	}

darkness {
		this.ledall(0);
		discovery.free;
		oscout.disconnect;
		seroscnet.disconnect;
	}

}
	