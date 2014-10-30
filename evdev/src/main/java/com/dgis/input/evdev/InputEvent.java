/**
 * This file is part of evdev-java - Java implementation.
 *
 * evdev-java - Java implementation is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * evdev-java - Java implementation is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with evdev-java - Java implementation.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.dgis.input.evdev;

import java.io.IOException;
import java.nio.ShortBuffer;

/**
 * Analog to the input_event struct in linux/input.h.
 * <p/>
 * Copyright (C) 2009 Giacomo Ferrari
 *
 * @author Giacomo Ferrari
 */

public class InputEvent {
    /**
     * size of the input_event struct in bytes.
     */
    public static final int STRUCT_SIZE_BYTES = 24;
    public static final int STRUCT_SIZE_BYTES_ARM = 16;

     /* Event types */

    public static final short EV_SYN = 0x00;
    public static final short EV_KEY = 0x01;
    public static final short EV_REL = 0x02;
    public static final short EV_ABS = 0x03;
    public static final short EV_MSC = 0x04;
    public static final short EV_SW = 0x05;
    public static final short EV_LED = 0x11;
    public static final short EV_SND = 0x12;
    public static final short EV_REP = 0x14;
    public static final short EV_FF = 0x15;
    public static final short EV_PWR = 0x16;
    public static final short EV_FF_STATUS = 0x17;
    public static final short EV_MAX = 0x1f;
    public static final short EV_CNT = (EV_MAX + 1);

    /* Synchronization events. */

    public static final short SYN_REPORT = 0;
    public static final short SYN_CONFIG = 1;
    public static final short SYN_MT_REPORT = 2;

    /*
     * Keys and buttons
     *
     * Most of the keys/buttons are modeled after USB HUT 1.12 (see
     * http://www.usb.org/developers/hidpage). Abbreviations in the comments: AC
     * - Application Control AL - Application Launch Button SC - System Control
     */

    public static final short KEY_RESERVED = 0;
    public static final short KEY_ESC = 1;
    public static final short KEY_1 = 2;
    public static final short KEY_2 = 3;
    public static final short KEY_3 = 4;
    public static final short KEY_4 = 5;
    public static final short KEY_5 = 6;
    public static final short KEY_6 = 7;
    public static final short KEY_7 = 8;
    public static final short KEY_8 = 9;
    public static final short KEY_9 = 10;
    public static final short KEY_0 = 11;
    public static final short KEY_MINUS = 12;
    public static final short KEY_EQUAL = 13;
    public static final short KEY_BACKSPACE = 14;
    public static final short KEY_TAB = 15;
    public static final short KEY_Q = 16;
    public static final short KEY_W = 17;
    public static final short KEY_E = 18;
    public static final short KEY_R = 19;
    public static final short KEY_T = 20;
    public static final short KEY_Y = 21;
    public static final short KEY_U = 22;
    public static final short KEY_I = 23;
    public static final short KEY_O = 24;
    public static final short KEY_P = 25;
    public static final short KEY_LEFTBRACE = 26;
    public static final short KEY_RIGHTBRACE = 27;
    public static final short KEY_ENTER = 28;
    public static final short KEY_LEFTCTRL = 29;
    public static final short KEY_A = 30;
    public static final short KEY_S = 31;
    public static final short KEY_D = 32;
    public static final short KEY_F = 33;
    public static final short KEY_G = 34;
    public static final short KEY_H = 35;
    public static final short KEY_J = 36;
    public static final short KEY_K = 37;
    public static final short KEY_L = 38;
    public static final short KEY_SEMICOLON = 39;
    public static final short KEY_APOSTROPHE = 40;
    public static final short KEY_GRAVE = 41;
    public static final short KEY_LEFTSHIFT = 42;
    public static final short KEY_BACKSLASH = 43;
    public static final short KEY_Z = 44;
    public static final short KEY_X = 45;
    public static final short KEY_C = 46;
    public static final short KEY_V = 47;
    public static final short KEY_B = 48;
    public static final short KEY_N = 49;
    public static final short KEY_M = 50;
    public static final short KEY_COMMA = 51;
    public static final short KEY_DOT = 52;
    public static final short KEY_SLASH = 53;
    public static final short KEY_RIGHTSHIFT = 54;
    public static final short KEY_KPASTERISK = 55;
    public static final short KEY_LEFTALT = 56;
    public static final short KEY_SPACE = 57;
    public static final short KEY_CAPSLOCK = 58;
    public static final short KEY_F1 = 59;
    public static final short KEY_F2 = 60;
    public static final short KEY_F3 = 61;
    public static final short KEY_F4 = 62;
    public static final short KEY_F5 = 63;
    public static final short KEY_F6 = 64;
    public static final short KEY_F7 = 65;
    public static final short KEY_F8 = 66;
    public static final short KEY_F9 = 67;
    public static final short KEY_F10 = 68;
    public static final short KEY_NUMLOCK = 69;
    public static final short KEY_SCROLLLOCK = 70;
    public static final short KEY_KP7 = 71;
    public static final short KEY_KP8 = 72;
    public static final short KEY_KP9 = 73;
    public static final short KEY_KPMINUS = 74;
    public static final short KEY_KP4 = 75;
    public static final short KEY_KP5 = 76;
    public static final short KEY_KP6 = 77;
    public static final short KEY_KPPLUS = 78;
    public static final short KEY_KP1 = 79;
    public static final short KEY_KP2 = 80;
    public static final short KEY_KP3 = 81;
    public static final short KEY_KP0 = 82;
    public static final short KEY_KPDOT = 83;

    public static final short KEY_ZENKAKUHANKAKU = 85;
    public static final short KEY_102ND = 86;
    public static final short KEY_F11 = 87;
    public static final short KEY_F12 = 88;
    public static final short KEY_RO = 89;
    public static final short KEY_KATAKANA = 90;
    public static final short KEY_HIRAGANA = 91;
    public static final short KEY_HENKAN = 92;
    public static final short KEY_KATAKANAHIRAGANA = 93;
    public static final short KEY_MUHENKAN = 94;
    public static final short KEY_KPJPCOMMA = 95;
    public static final short KEY_KPENTER = 96;
    public static final short KEY_RIGHTCTRL = 97;
    public static final short KEY_KPSLASH = 98;
    public static final short KEY_SYSRQ = 99;
    public static final short KEY_RIGHTALT = 100;
    public static final short KEY_LINEFEED = 101;
    public static final short KEY_HOME = 102;
    public static final short KEY_UP = 103;
    public static final short KEY_PAGEUP = 104;
    public static final short KEY_LEFT = 105;
    public static final short KEY_RIGHT = 106;
    public static final short KEY_END = 107;
    public static final short KEY_DOWN = 108;
    public static final short KEY_PAGEDOWN = 109;
    public static final short KEY_INSERT = 110;
    public static final short KEY_DELETE = 111;
    public static final short KEY_MACRO = 112;
    public static final short KEY_MUTE = 113;
    public static final short KEY_VOLUMEDOWN = 114;
    public static final short KEY_VOLUMEUP = 115;
    /** SC System Power Down */
    public static final short KEY_POWER = 116;
    public static final short KEY_KPEQUAL = 117;
    public static final short KEY_KPPLUSMINUS = 118;
    public static final short KEY_PAUSE = 119;
    /** AL Compiz Scale (Expose) */
    public static final short KEY_SCALE = 120;

    public static final short KEY_KPCOMMA = 121;
    public static final short KEY_HANGEUL = 122;
    public static final short KEY_HANGUEL = KEY_HANGEUL;
    public static final short KEY_HANJA = 123;
    public static final short KEY_YEN = 124;
    public static final short KEY_LEFTMETA = 125;
    public static final short KEY_RIGHTMETA = 126;
    public static final short KEY_COMPOSE = 127;

    /** AC Stop */
    public static final short KEY_STOP = 128;
    public static final short KEY_AGAIN = 129;
    /** AC Properties */
    public static final short KEY_PROPS = 130;
    /** AC Undo */
    public static final short KEY_UNDO = 131;
    public static final short KEY_FRONT = 132;
    /** AC Copy */
    public static final short KEY_COPY = 133;
    /** AC Open */
    public static final short KEY_OPEN = 134;
    /** AC Paste */
    public static final short KEY_PASTE = 135;
    /** AC Search */
    public static final short KEY_FIND = 136;
    /** AC Cut */
    public static final short KEY_CUT = 137;
    /** AL Integrated Help Center */
    public static final short KEY_HELP = 138;
    /** Menu (show menu) */
    public static final short KEY_MENU = 139;
    /** AL Calculator */
    public static final short KEY_CALC = 140;
    public static final short KEY_SETUP = 141;
    /** SC System Sleep */
    public static final short KEY_SLEEP = 142;
    /** System Wake Up */
    public static final short KEY_WAKEUP = 143;
    /** AL Local Machine Browser */
    public static final short KEY_FILE = 144;
    public static final short KEY_SENDFILE = 145;
    public static final short KEY_DELETEFILE = 146;
    public static final short KEY_XFER = 147;
    public static final short KEY_PROG1 = 148;
    public static final short KEY_PROG2 = 149;
    /** AL Internet Browser */
    public static final short KEY_WWW = 150;
    public static final short KEY_MSDOS = 151;
    /** AL Terminal Lock/Screensaver */
    public static final short KEY_COFFEE = 152;
    public static final short KEY_SCREENLOCK = KEY_COFFEE;
    public static final short KEY_DIRECTION = 153;
    public static final short KEY_CYCLEWINDOWS = 154;
    public static final short KEY_MAIL = 155;
    /** AC Bookmarks */
    public static final short KEY_BOOKMARKS = 156;
    public static final short KEY_COMPUTER = 157;
    /** AC Back */
    public static final short KEY_BACK = 158;
    /** AC Forward */
    public static final short KEY_FORWARD = 159;
    public static final short KEY_CLOSECD = 160;
    public static final short KEY_EJECTCD = 161;
    public static final short KEY_EJECTCLOSECD = 162;
    public static final short KEY_NEXTSONG = 163;
    public static final short KEY_PLAYPAUSE = 164;
    public static final short KEY_PREVIOUSSONG = 165;
    public static final short KEY_STOPCD = 166;
    public static final short KEY_RECORD = 167;
    public static final short KEY_REWIND = 168;
    /** Media Select Telephone */
    public static final short KEY_PHONE = 169;
    public static final short KEY_ISO = 170;
    /** AL Consumer Control Configuration */
    public static final short KEY_CONFIG = 171;
    /** AC Home */
    public static final short KEY_HOMEPAGE = 172;
    /** AC Refresh */
    public static final short KEY_REFRESH = 173;
    /** AC Exit */
    public static final short KEY_EXIT = 174;
    public static final short KEY_MOVE = 175;
    public static final short KEY_EDIT = 176;
    public static final short KEY_SCROLLUP = 177;
    public static final short KEY_SCROLLDOWN = 178;
    public static final short KEY_KPLEFTPAREN = 179;
    public static final short KEY_KPRIGHTPAREN = 180;
    /** AC New */
    public static final short KEY_NEW = 181;
    /** AC Redo/Repeat */
    public static final short KEY_REDO = 182;

    public static final short KEY_F13 = 183;
    public static final short KEY_F14 = 184;
    public static final short KEY_F15 = 185;
    public static final short KEY_F16 = 186;
    public static final short KEY_F17 = 187;
    public static final short KEY_F18 = 188;
    public static final short KEY_F19 = 189;
    public static final short KEY_F20 = 190;
    public static final short KEY_F21 = 191;
    public static final short KEY_F22 = 192;
    public static final short KEY_F23 = 193;
    public static final short KEY_F24 = 194;

    public static final short KEY_PLAYCD = 200;
    public static final short KEY_PAUSECD = 201;
    public static final short KEY_PROG3 = 202;
    public static final short KEY_PROG4 = 203;
    /** AL Dashboard */
    public static final short KEY_DASHBOARD = 204;
    public static final short KEY_SUSPEND = 205;
    /** AC Close */
    public static final short KEY_CLOSE = 206;
    public static final short KEY_PLAY = 207;
    public static final short KEY_FASTFORWARD = 208;
    public static final short KEY_BASSBOOST = 209;
    /** AC Print */
    public static final short KEY_PRINT = 210;
    public static final short KEY_HP = 211;
    public static final short KEY_CAMERA = 212;
    public static final short KEY_SOUND = 213;
    public static final short KEY_QUESTION = 214;
    public static final short KEY_EMAIL = 215;
    public static final short KEY_CHAT = 216;
    public static final short KEY_SEARCH = 217;
    public static final short KEY_CONNECT = 218;
    /** AL Checkbook/Finance */
    public static final short KEY_FINANCE = 219;
    public static final short KEY_SPORT = 220;
    public static final short KEY_SHOP = 221;
    public static final short KEY_ALTERASE = 222;
    /** AC Cancel */
    public static final short KEY_CANCEL = 223;
    public static final short KEY_BRIGHTNESSDOWN = 224;
    public static final short KEY_BRIGHTNESSUP = 225;
    public static final short KEY_MEDIA = 226;

    /** Cycle between available video outputs (Monitor/LCD/TV-out/etc) */
    public static final short KEY_SWITCHVIDEOMODE = 227;
    public static final short KEY_KBDILLUMTOGGLE = 228;
    public static final short KEY_KBDILLUMDOWN = 229;
    public static final short KEY_KBDILLUMUP = 230;

    /** AC Send */
    public static final short KEY_SEND = 231;
    /** AC Reply */
    public static final short KEY_REPLY = 232;
    /** AC Forward Msg */
    public static final short KEY_FORWARDMAIL = 233;
    /** AC Save */
    public static final short KEY_SAVE = 234;
    public static final short KEY_DOCUMENTS = 235;

    public static final short KEY_BATTERY = 236;

    public static final short KEY_BLUETOOTH = 237;
    public static final short KEY_WLAN = 238;
    public static final short KEY_UWB = 239;

    public static final short KEY_UNKNOWN = 240;

    /** drive next video source */
    public static final short KEY_VIDEO_NEXT = 241;
    /** drive previous video source */
    public static final short KEY_VIDEO_PREV = 242;
    /** brightness up, after max is min */
    public static final short KEY_BRIGHTNESS_CYCLE = 243;
    /** brightness off, use ambient */
    public static final short KEY_BRIGHTNESS_ZERO = 244;
    /** display device to off state */
    public static final short KEY_DISPLAY_OFF = 245;

    public static final short KEY_WIMAX = 246;

    /* Range 248 - 255 is reserved for special needs of AT keyboard driver */

    public static final short BTN_MISC = 0x100;
    public static final short BTN_0 = 0x100;
    public static final short BTN_1 = 0x101;
    public static final short BTN_2 = 0x102;
    public static final short BTN_3 = 0x103;
    public static final short BTN_4 = 0x104;
    public static final short BTN_5 = 0x105;
    public static final short BTN_6 = 0x106;
    public static final short BTN_7 = 0x107;
    public static final short BTN_8 = 0x108;
    public static final short BTN_9 = 0x109;

    public static final short BTN_MOUSE = 0x110;
    public static final short BTN_LEFT = 0x110;
    public static final short BTN_RIGHT = 0x111;
    public static final short BTN_MIDDLE = 0x112;
    public static final short BTN_SIDE = 0x113;
    public static final short BTN_EXTRA = 0x114;
    public static final short BTN_FORWARD = 0x115;
    public static final short BTN_BACK = 0x116;
    public static final short BTN_TASK = 0x117;

    public static final short BTN_JOYSTICK = 0x120;
    public static final short BTN_TRIGGER = 0x120;
    public static final short BTN_THUMB = 0x121;
    public static final short BTN_THUMB2 = 0x122;
    public static final short BTN_TOP = 0x123;
    public static final short BTN_TOP2 = 0x124;
    public static final short BTN_PINKIE = 0x125;
    public static final short BTN_BASE = 0x126;
    public static final short BTN_BASE2 = 0x127;
    public static final short BTN_BASE3 = 0x128;
    public static final short BTN_BASE4 = 0x129;
    public static final short BTN_BASE5 = 0x12a;
    public static final short BTN_BASE6 = 0x12b;
    public static final short BTN_DEAD = 0x12f;

    public static final short BTN_GAMEPAD = 0x130;
    public static final short BTN_A = 0x130;
    public static final short BTN_B = 0x131;
    public static final short BTN_C = 0x132;
    public static final short BTN_X = 0x133;
    public static final short BTN_Y = 0x134;
    public static final short BTN_Z = 0x135;
    public static final short BTN_TL = 0x136;
    public static final short BTN_TR = 0x137;
    public static final short BTN_TL2 = 0x138;
    public static final short BTN_TR2 = 0x139;
    public static final short BTN_SELECT = 0x13a;
    public static final short BTN_START = 0x13b;
    public static final short BTN_MODE = 0x13c;
    public static final short BTN_THUMBL = 0x13d;
    public static final short BTN_THUMBR = 0x13e;

    public static final short BTN_DIGI = 0x140;
    public static final short BTN_TOOL_PEN = 0x140;
    public static final short BTN_TOOL_RUBBER = 0x141;
    public static final short BTN_TOOL_BRUSH = 0x142;
    public static final short BTN_TOOL_PENCIL = 0x143;
    public static final short BTN_TOOL_AIRBRUSH = 0x144;
    public static final short BTN_TOOL_FINGER = 0x145;
    public static final short BTN_TOOL_MOUSE = 0x146;
    public static final short BTN_TOOL_LENS = 0x147;
    public static final short BTN_TOUCH = 0x14a;
    public static final short BTN_STYLUS = 0x14b;
    public static final short BTN_STYLUS2 = 0x14c;
    public static final short BTN_TOOL_DOUBLETAP = 0x14d;
    public static final short BTN_TOOL_TRIPLETAP = 0x14e;
    /** Four fingers on trackpad */
    public static final short BTN_TOOL_QUADTAP = 0x14f;

    public static final short BTN_WHEEL = 0x150;
    public static final short BTN_GEAR_DOWN = 0x150;
    public static final short BTN_GEAR_UP = 0x151;

    public static final short KEY_OK = 0x160;
    public static final short KEY_SELECT = 0x161;
    public static final short KEY_GOTO = 0x162;
    public static final short KEY_CLEAR = 0x163;
    public static final short KEY_POWER2 = 0x164;
    public static final short KEY_OPTION = 0x165;
    /** AL OEM Features/Tips/Tutorial */
    public static final short KEY_INFO = 0x166;
    public static final short KEY_TIME = 0x167;
    public static final short KEY_VENDOR = 0x168;
    public static final short KEY_ARCHIVE = 0x169;
    /** Media Select Program Guide */
    public static final short KEY_PROGRAM = 0x16a;
    public static final short KEY_CHANNEL = 0x16b;
    public static final short KEY_FAVORITES = 0x16c;
    public static final short KEY_EPG = 0x16d;
    /** Media Select Home */
    public static final short KEY_PVR = 0x16e;
    public static final short KEY_MHP = 0x16f;
    public static final short KEY_LANGUAGE = 0x170;
    public static final short KEY_TITLE = 0x171;
    public static final short KEY_SUBTITLE = 0x172;
    public static final short KEY_ANGLE = 0x173;
    public static final short KEY_ZOOM = 0x174;
    public static final short KEY_MODE = 0x175;
    public static final short KEY_KEYBOARD = 0x176;
    public static final short KEY_SCREEN = 0x177;
    /** Media Select Computer */
    public static final short KEY_PC = 0x178;
    /** Media Select TV */
    public static final short KEY_TV = 0x179;
    /** Media Select Cable */
    public static final short KEY_TV2 = 0x17a;
    /** Media Select VCR */
    public static final short KEY_VCR = 0x17b;
    /** VCR Plus */
    public static final short KEY_VCR2 = 0x17c;
    /** Media Select Satellite */
    public static final short KEY_SAT = 0x17d;
    public static final short KEY_SAT2 = 0x17e;
    /** Media Select CD */
    public static final short KEY_CD = 0x17f;
    /** Media Select Tape */
    public static final short KEY_TAPE = 0x180;
    public static final short KEY_RADIO = 0x181;
    /** Media Select Tuner */
    public static final short KEY_TUNER = 0x182;
    public static final short KEY_PLAYER = 0x183;
    public static final short KEY_TEXT = 0x184;
    /** Media Select DVD */
    public static final short KEY_DVD = 0x185;
    public static final short KEY_AUX = 0x186;
    public static final short KEY_MP3 = 0x187;
    public static final short KEY_AUDIO = 0x188;
    public static final short KEY_VIDEO = 0x189;
    public static final short KEY_DIRECTORY = 0x18a;
    public static final short KEY_LIST = 0x18b;
    /** Media Select Messages */
    public static final short KEY_MEMO = 0x18c;
    public static final short KEY_CALENDAR = 0x18d;
    public static final short KEY_RED = 0x18e;
    public static final short KEY_GREEN = 0x18f;
    public static final short KEY_YELLOW = 0x190;
    public static final short KEY_BLUE = 0x191;
    /** Channel Increment */
    public static final short KEY_CHANNELUP = 0x192;
    /** Channel Decrement */
    public static final short KEY_CHANNELDOWN = 0x193;
    public static final short KEY_FIRST = 0x194;
    /** Recall Last */
    public static final short KEY_LAST = 0x195;
    public static final short KEY_AB = 0x196;
    public static final short KEY_NEXT = 0x197;
    public static final short KEY_RESTART = 0x198;
    public static final short KEY_SLOW = 0x199;
    public static final short KEY_SHUFFLE = 0x19a;
    public static final short KEY_BREAK = 0x19b;
    public static final short KEY_PREVIOUS = 0x19c;
    public static final short KEY_DIGITS = 0x19d;
    public static final short KEY_TEEN = 0x19e;
    public static final short KEY_TWEN = 0x19f;
    /** Media Select Video Phone */
    public static final short KEY_VIDEOPHONE = 0x1a0;
    /** Media Select Games */
    public static final short KEY_GAMES = 0x1a1;
    /** AC Zoom In */
    public static final short KEY_ZOOMIN = 0x1a2;
    /** AC Zoom Out */
    public static final short KEY_ZOOMOUT = 0x1a3;
    /** AC Zoom */
    public static final short KEY_ZOOMRESET = 0x1a4;
    /** AL Word Processor */
    public static final short KEY_WORDPROCESSOR = 0x1a5;
    /** AL Text Editor */
    public static final short KEY_EDITOR = 0x1a6;
    /** AL Spreadsheet */
    public static final short KEY_SPREADSHEET = 0x1a7;
    /** AL Graphics Editor */
    public static final short KEY_GRAPHICSEDITOR = 0x1a8;
    /** AL Presentation App */
    public static final short KEY_PRESENTATION = 0x1a9;
    /** AL Database App */
    public static final short KEY_DATABASE = 0x1aa;
    /** AL Newsreader */
    public static final short KEY_NEWS = 0x1ab;
    /** AL Voicemail */
    public static final short KEY_VOICEMAIL = 0x1ac;
    /** AL Contacts/Address Book */
    public static final short KEY_ADDRESSBOOK = 0x1ad;
    /** AL Instant Messaging */
    public static final short KEY_MESSENGER = 0x1ae;
    /** Turn display (LCD) on and off */
    public static final short KEY_DISPLAYTOGGLE = 0x1af;
    /** AL Spell Check */
    public static final short KEY_SPELLCHECK = 0x1b0;
    /** AL Logoff */
    public static final short KEY_LOGOFF = 0x1b1;

    public static final short KEY_DOLLAR = 0x1b2;
    public static final short KEY_EURO = 0x1b3;

    /** Consumer - transport controls */
    public static final short KEY_FRAMEBACK = 0x1b4;
    public static final short KEY_FRAMEFORWARD = 0x1b5;
    /** GenDesc - system context menu */
    public static final short KEY_CONTEXT_MENU = 0x1b6;
    /** Consumer - transport control */
    public static final short KEY_MEDIA_REPEAT = 0x1b7;

    public static final short KEY_DEL_EOL = 0x1c0;
    public static final short KEY_DEL_EOS = 0x1c1;
    public static final short KEY_INS_LINE = 0x1c2;
    public static final short KEY_DEL_LINE = 0x1c3;

    public static final short KEY_FN = 0x1d0;
    public static final short KEY_FN_ESC = 0x1d1;
    public static final short KEY_FN_F1 = 0x1d2;
    public static final short KEY_FN_F2 = 0x1d3;
    public static final short KEY_FN_F3 = 0x1d4;
    public static final short KEY_FN_F4 = 0x1d5;
    public static final short KEY_FN_F5 = 0x1d6;
    public static final short KEY_FN_F6 = 0x1d7;
    public static final short KEY_FN_F7 = 0x1d8;
    public static final short KEY_FN_F8 = 0x1d9;
    public static final short KEY_FN_F9 = 0x1da;
    public static final short KEY_FN_F10 = 0x1db;
    public static final short KEY_FN_F11 = 0x1dc;
    public static final short KEY_FN_F12 = 0x1dd;
    public static final short KEY_FN_1 = 0x1de;
    public static final short KEY_FN_2 = 0x1df;
    public static final short KEY_FN_D = 0x1e0;
    public static final short KEY_FN_E = 0x1e1;
    public static final short KEY_FN_F = 0x1e2;
    public static final short KEY_FN_S = 0x1e3;
    public static final short KEY_FN_B = 0x1e4;

    public static final short KEY_BRL_DOT1 = 0x1f1;
    public static final short KEY_BRL_DOT2 = 0x1f2;
    public static final short KEY_BRL_DOT3 = 0x1f3;
    public static final short KEY_BRL_DOT4 = 0x1f4;
    public static final short KEY_BRL_DOT5 = 0x1f5;
    public static final short KEY_BRL_DOT6 = 0x1f6;
    public static final short KEY_BRL_DOT7 = 0x1f7;
    public static final short KEY_BRL_DOT8 = 0x1f8;
    public static final short KEY_BRL_DOT9 = 0x1f9;
    public static final short KEY_BRL_DOT10 = 0x1fa;

    /* used by phones, remote controls */
    public static final short KEY_NUMERIC_0 = 0x200;
    public static final short KEY_NUMERIC_1 = 0x201;
    public static final short KEY_NUMERIC_2 = 0x202;
    public static final short KEY_NUMERIC_3 = 0x203;
    public static final short KEY_NUMERIC_4 = 0x204;
    public static final short KEY_NUMERIC_5 = 0x205;
    public static final short KEY_NUMERIC_6 = 0x206;
    public static final short KEY_NUMERIC_7 = 0x207;
    public static final short KEY_NUMERIC_8 = 0x208;
    public static final short KEY_NUMERIC_9 = 0x209;
    public static final short KEY_NUMERIC_STAR = 0x20a;
    public static final short KEY_NUMERIC_POUND = 0x20b;

    /* We avoid low common keys in module aliases so they don't get huge. */
    public static final short KEY_MIN_INTERESTING = KEY_MUTE;
    public static final short KEY_MAX = 0x2ff;
    public static final short KEY_CNT = (KEY_MAX + 1);

    /* Relative axes */

    public static final short REL_X = 0x00;
    public static final short REL_Y = 0x01;
    public static final short REL_Z = 0x02;
    public static final short REL_RX = 0x03;
    public static final short REL_RY = 0x04;
    public static final short REL_RZ = 0x05;
    public static final short REL_HWHEEL = 0x06;
    public static final short REL_DIAL = 0x07;
    public static final short REL_WHEEL = 0x08;
    public static final short REL_MISC = 0x09;
    public static final short REL_MAX = 0x0f;
    public static final short REL_CNT = (REL_MAX + 1);

    /* Absolute axes */

    public static final short ABS_X = 0x00;
    public static final short ABS_Y = 0x01;
    public static final short ABS_Z = 0x02;
    public static final short ABS_RX = 0x03;
    public static final short ABS_RY = 0x04;
    public static final short ABS_RZ = 0x05;
    public static final short ABS_THROTTLE = 0x06;
    public static final short ABS_RUDDER = 0x07;
    public static final short ABS_WHEEL = 0x08;
    public static final short ABS_GAS = 0x09;
    public static final short ABS_BRAKE = 0x0a;
    public static final short ABS_HAT0X = 0x10;
    public static final short ABS_HAT0Y = 0x11;
    public static final short ABS_HAT1X = 0x12;
    public static final short ABS_HAT1Y = 0x13;
    public static final short ABS_HAT2X = 0x14;
    public static final short ABS_HAT2Y = 0x15;
    public static final short ABS_HAT3X = 0x16;
    public static final short ABS_HAT3Y = 0x17;
    public static final short ABS_PRESSURE = 0x18;
    public static final short ABS_DISTANCE = 0x19;
    public static final short ABS_TILT_X = 0x1a;
    public static final short ABS_TILT_Y = 0x1b;
    public static final short ABS_TOOL_WIDTH = 0x1c;
    public static final short ABS_VOLUME = 0x20;
    public static final short ABS_MISC = 0x28;

    /** Major axis of touching ellipse */
    public static final short ABS_MT_TOUCH_MAJOR = 0x30;
    /** Minor axis (omit if circular) */
    public static final short ABS_MT_TOUCH_MINOR = 0x31;
    /** Major axis of approaching ellipse */
    public static final short ABS_MT_WIDTH_MAJOR = 0x32;
    /** Minor axis (omit if circular) */
    public static final short ABS_MT_WIDTH_MINOR = 0x33;
    /** Ellipse orientation */
    public static final short ABS_MT_ORIENTATION = 0x34;
    /** Center X ellipse position */
    public static final short ABS_MT_POSITION_X = 0x35;
    /** Center Y ellipse position */
    public static final short ABS_MT_POSITION_Y = 0x36;
    /** Type of touching device */
    public static final short ABS_MT_TOOL_TYPE = 0x37;
    /** Group a set of packets as a blob */
    public static final short ABS_MT_BLOB_ID = 0x38;
    /** Unique ID of initiated contact */
    public static final short ABS_MT_TRACKING_ID = 0x39;

    public static final short ABS_MAX = 0x3f;
    public static final short ABS_CNT = (ABS_MAX + 1);

    /* Switch events */
    /** set = lid shut */
    public static final short SW_LID = 0x00;
    /** set = tablet mode */
    public static final short SW_TABLET_MODE = 0x01;
    /** set = inserted */
    public static final short SW_HEADPHONE_INSERT = 0x02;
    /** rfkill master switch, type "any" set = radio enabled */
    public static final short SW_RFKILL_ALL = 0x03;
    /** @deprecated  */
    public static final short SW_RADIO = SW_RFKILL_ALL;
    /** set = inserted */
    public static final short SW_MICROPHONE_INSERT = 0x04;
    /** set = plugged into dock */
    public static final short SW_DOCK = 0x05;
    /** set = inserted */
    public static final short SW_LINEOUT_INSERT = 0x06;
    /** set = mechanical switch set */
    public static final short SW_JACK_PHYSICAL_INSERT = 0x07;
    /** set = inserted */
    public static final short SW_VIDEOOUT_INSERT = 0x08;
    public static final short SW_MAX = 0x0f;
    public static final short SW_CNT = (SW_MAX + 1);

    /* Misc events */

    public static final short MSC_SERIAL = 0x00;
    public static final short MSC_PULSELED = 0x01;
    public static final short MSC_GESTURE = 0x02;
    public static final short MSC_RAW = 0x03;
    public static final short MSC_SCAN = 0x04;
    public static final short MSC_MAX = 0x07;
    public static final short MSC_CNT = (MSC_MAX + 1);

    /* LEDs */

    public static final short LED_NUML = 0x00;
    public static final short LED_CAPSL = 0x01;
    public static final short LED_SCROLLL = 0x02;
    public static final short LED_COMPOSE = 0x03;
    public static final short LED_KANA = 0x04;
    public static final short LED_SLEEP = 0x05;
    public static final short LED_SUSPEND = 0x06;
    public static final short LED_MUTE = 0x07;
    public static final short LED_MISC = 0x08;
    public static final short LED_MAIL = 0x09;
    public static final short LED_CHARGING = 0x0a;
    public static final short LED_MAX = 0x0f;
    public static final short LED_CNT = (LED_MAX + 1);

    /* Autorepeat values */

    public static final short REP_DELAY = 0x00;
    public static final short REP_PERIOD = 0x01;
    public static final short REP_MAX = 0x01;

    /* Sounds */

    public static final short SND_CLICK = 0x00;
    public static final short SND_BELL = 0x01;
    public static final short SND_TONE = 0x02;
    public static final short SND_MAX = 0x07;
    public static final short SND_CNT = (SND_MAX + 1);

    /* IDs. */

    public static final short ID_BUS = 0;
    public static final short ID_VENDOR = 1;
    public static final short ID_PRODUCT = 2;
    public static final short ID_VERSION = 3;

    public static final short BUS_PCI = 0x01;
    public static final short BUS_ISAPNP = 0x02;
    public static final short BUS_USB = 0x03;
    public static final short BUS_HIL = 0x04;
    public static final short BUS_BLUETOOTH = 0x05;
    public static final short BUS_VIRTUAL = 0x06;

    public static final short BUS_ISA = 0x10;
    public static final short BUS_I8042 = 0x11;
    public static final short BUS_XTKBD = 0x12;
    public static final short BUS_RS232 = 0x13;
    public static final short BUS_GAMEPORT = 0x14;
    public static final short BUS_PARPORT = 0x15;
    public static final short BUS_AMIGA = 0x16;
    public static final short BUS_ADB = 0x17;
    public static final short BUS_I2C = 0x18;
    public static final short BUS_HOST = 0x19;
    public static final short BUS_GSC = 0x1A;
    public static final short BUS_ATARI = 0x1B;

    /* MT_TOOL types */
    public static final short MT_TOOL_FINGER = 0;
    public static final short MT_TOOL_PEN = 1;

    /* Values describing the status of a force-feedback effect */
    public static final short FF_STATUS_STOPPED = 0x00;
    public static final short FF_STATUS_PLAYING = 0x01;
    public static final short FF_STATUS_MAX = 0x01;


    public final long timeSec;
    public final long timeMicroSec;
    public final /*__u16*/ short type;
    public final /*__u16*/ short code;
    public final /*__s32*/ int value;
    public final String source;

    public InputEvent(long timeSec, long timeMicroSec, short type, short code, int value, String source) {
        this.timeSec = timeSec;
        this.timeMicroSec = timeMicroSec;
        this.type = type;
        this.code = code;
        this.value = value;
        this.source = source;
    }

    /**
     * Parse an InputEvent out of a ShortBuffer.
     *
     * @param shortBuffer
     * @param source
     * @return the parsed InputEvent
     * @throws IOException
     */
    public static InputEvent parse(ShortBuffer shortBuffer, String source) throws IOException {
        return parse(shortBuffer, source, null);
    }

    /**
     * Parse an InputEvent out of a ShortBuffer.
     *
     * @param shortBuffer
     * @param source
     * @param arch
     * @return the parsed InputEvent
     * @throws IOException
     */
    public static InputEvent parse(ShortBuffer shortBuffer, String source, String arch) throws IOException {
        short a, b, c, d;
        a = shortBuffer.get();
        b = shortBuffer.get();
        long timeSec;
        if (arch.equals("arm")) {
            timeSec = (b << 16) | a;
        } else {
            c = shortBuffer.get();
            d = shortBuffer.get();
            timeSec = ((long)d << 48) | ((long)c << 32) | ((long)b << 16) | a;
        }
        a = shortBuffer.get();
        b = shortBuffer.get();
        long timeMicroSec;
        if (arch.equals("arm")) {
            timeMicroSec = (b << 16) | a;
        } else {
            c = shortBuffer.get();
            d = shortBuffer.get();
            // fixme : longs are signed, so this is probably not doing what we expect it to do
            timeMicroSec = (d << 48) | (c << 32) | (b << 16) | a;
        }
        short type = shortBuffer.get();
        short code = shortBuffer.get();
        c = shortBuffer.get();
        d = shortBuffer.get();
        int value = (d << 16) | c;

        return new InputEvent(timeSec, timeMicroSec, type, code, value, source);
    }

    @Override
    public String toString() {
        //TODO Java sucks at printing unsigned longs. Dur...
        return String.format("Event: time %d.%06d, type %d, code %d, value %02x",
                timeSec, timeMicroSec, type, code, value);
    }

}
