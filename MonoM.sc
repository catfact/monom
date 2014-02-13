/*

for information about monome devices:
monome.org

written by:
raja das
https://github.com/Karaokaze

*/

MonoM {

    classvar seroscnet, discovery, <>rows, <>columns, <>portlst;
    var <>prefix, <>rot, <>dvcnum, oscout;

    *initClass {

        var sz, rw, cl;

        portlst = List.new(0);
        rows = List.new(0);
        columns = List.new(0);
        seroscnet = NetAddr.new("localhost", 12002);
        seroscnet.sendMsg("/serialosc/list", "127.0.0.1", NetAddr.localAddr.port);

        StartUp.add {

            discovery = OSCdef.newMatching(\monomediscover,
                {|msg, time, addr, recvPort|

                    sz = msg[2].asString.replace("monome","").replace("40h",64).asInteger;
                    rw = case
                    {sz == 64} {8}
                    {sz == 128}{8}
                    {sz == 256}{16};
                    cl = case
                    {sz == 64} {8}
                    {sz == 128}{16}
                    {sz == 256}{16};
                    rows.add(rw);
                    columns.add(cl);
                    portlst.add(msg[3]);
                    ("Device connected on port:"++msg[3]).postln;
                    msg[1].postln;
                    msg[2].postln;

            }, '/serialosc/device', seroscnet);

        }

    }

    *new { arg prefix, rot;

        ^super.new.init
        (prefix, rot);

    }

    init { arg prefix_, rot_;

        prefix = prefix_;
        rot = rot_;

    }

    deviceList {

        portlst.clear; rows.clear; columns.clear;
        seroscnet.sendMsg("/serialosc/list", "127.0.0.1", NetAddr.localAddr.port);

    }

    printOn { arg stream;
        stream << "Ports:" << portlst;

    }


    useDevice { arg devicenum;

        dvcnum = devicenum;
        oscout = NetAddr.new("localhost", portlst[devicenum].value);
        Post << "Using device on port#" << portlst[devicenum].value << Char.nl;

        oscout.sendMsg("/sys/port", NetAddr.localAddr.port);
        oscout.sendMsg("/sys/prefix", prefix);
        oscout.sendMsg("/sys/rotation", rot);
    }

    usePort { arg portnum;

        dvcnum = portlst.indexOf(portnum);
        oscout = NetAddr.new("localhost", portnum);
        Post << "Using device#" << dvcnum << Char.nl;

        oscout.sendMsg("/sys/port", NetAddr.localAddr.port);
        oscout.sendMsg("/sys/prefix", prefix);
        oscout.sendMsg("/sys/rotation", rot);
    }

    prt {
        ^portlst[dvcnum];
    }

    rws {
        ^rows[dvcnum];
    }

    cls {
        ^columns[dvcnum];
    }
    // See here: http://monome.org/docs/tech:osc
    // if you need further explanation of the led methods below
    ledset	{ arg col, row, noff;
        oscout.sendMsg(prefix++"/grid/led/set", col, row, noff);
    }

    ledall 	{ arg noff;
        oscout.sendMsg(prefix++"/grid/led/all", noff);
    }

    ledmap	{ arg exoff, whyoff, larr;
        oscout.sendMsg(prefix++"/grid/led/map", exoff, whyoff, *larr);

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
        oscout.sendMsg(prefix++"/grid/led/level/map", exoff, whyoff, *larr);

    }

    levrow	{ arg exoff, why, larr;
        oscout.sendMsg(prefix++"/grid/led/level/row", exoff, why,*larr);

    }

    levcol	{ arg ex, whyoff, larr;
        oscout.sendMsg(prefix++"/grid/led/level/col", ex, whyoff,*larr);

    }


    tiltnoff { arg sens, noff;
        oscout.sendMsg(prefix++"/tilt/set", sens, noff);
    }

    darkness {

        this.ledall(0);
        discovery.free;
        oscout.disconnect;
        seroscnet.disconnect;
    }

}

