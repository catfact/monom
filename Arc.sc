Arc : MonoM {

    var scaleFactor;

    *new { | prefix, rot |

        ^super.new.initArc(prefix, rot);
    }

    initArc { | your_prefix, your_rotation |

        prefix = your_prefix;
        rot = your_rotation;

        case
        { rot == 0 } { scaleFactor = 0 }
        { rot == 90 } { scaleFactor = 16 }
        { rot == 180 } { scaleFactor = 32 }
        { rot == 270 } { scaleFactor = 48 }
        { ((rot == 0) or: (rot == 90) or: (rot == 180) or: (rot == 270)).not }
        {
            "Did not choose valid rotation. Using default: 0".warn;
            scaleFactor = 0;
            rot = 0;
        };

    }

    ringset { | enc, led, lev |

        oscout.sendMsg(prefix++"/ring/set", enc,
            (led + scaleFactor).wrap(0, 63),
            lev);
    }

    ringall { | enc, lev |
        oscout.sendMsg(prefix++"/ring/all", enc, lev);
    }

    ringmap	{ | enc, larr |

        scaleFactor.do({

            larr = larr.shift(1, larr @ (larr.size - 1));

        });

        oscout.sendMsg(prefix++"/ring/map", enc, *larr);

    }

    ringrange { | enc, led1, led2, lev |

        oscout.sendMsg(prefix++"/ring/range", enc, led1+scaleFactor, led2+scaleFactor, lev);
    }

    // exercise caution when changing rotation
    // after change, your led positions may not be desirable.
    rot_ { arg degree;

        rot = degree;

        case
        { rot == 0 } { scaleFactor = 0 }
        { rot == 90 } { scaleFactor = 16 }
        { rot == 180 } { scaleFactor = 32 }
        { rot == 270 } { scaleFactor = 48 }
        { (rot != 0) or: (rot != 90) or: (rot != 180) or: (rot != 270) } {

            "Did not choose valid rotation (0, 90, 180, 270). Using default: 0.".warn;
            scaleFactor = 0;
            rot = 0;
        };

        // flash one LED indicating north position
        for(0, 3, { arg i; this.ringall(i, 0);});

        4.do({
            for(0, 3, { arg i;

                for(0, 30, { arg brightness;

                    this.ringset(i, 0, brightness.fold(0, 15));
                });

            });
        });

    }


    darkness {
        for(0, 3, { arg i; this.ringall(i, 0);});
        discovery.free;
        oscout.disconnect;
        seroscnet.disconnect;
    }
}