<?php
//header('Access-Control-Allow-Origin: http://*.baidu.com'); //设置http://*.baidu.com允许跨域访问
date_default_timezone_set("Asia/chongqing");
error_reporting(E_ERROR);
header("Content-Type: text/html; charset=utf-8");
define(BD_EDITOR_ROOT,substr ( dirname ( __FILE__ ), 0, - 22 ));
$CONFIG = json_decode(preg_replace("/\/\*[\s\S]+?\*\//", "", file_get_contents("config.json")), true);
$action = $_GET['action'];
define ( "IN_KEKE", TRUE );
include BD_EDITOR_ROOT.'/app_comm.php';

if(!QN_UPLOAD_OPEN)
{
    $CONFIG['imageUrlPrefix'] = $CONFIG['scrawlUrlPrefix'] = $CONFIG['snapscreenUrlPrefix'] = $CONFIG['catcherUrlPrefix'] = $CONFIG['videoUrlPrefix'] = $CONFIG['fileUrlPrefix'] = $CONFIG['fileManagerUrlPrefix'] =  $_K['siteurl'].'/';
}


switch ($action) {
    case 'config':
        $result =  json_encode($CONFIG);
        break;

    /* 上传图片 */
    case 'uploadimage':
    /* 上传涂鸦 */
    case 'uploadscrawl':
    /* 上传视频 */
    case 'uploadvideo':
    /* 上传文件 */
    case 'uploadfile':
        $result = include("action_upload.php");
        break;

    /* 列出图片 */
    case 'listimage':
        $result = include("action_list.php");
        break;
    /* 列出文件 */
    case 'listfile':
        $result = include("action_list.php");
        break;

    /* 抓取远程文件 */
    case 'catchimage':
        $result = include("action_crawler.php");
        break;

    default:
        $result = json_encode(array(
            'state'=> '请求地址出错'
        ));
        break;
}

/* 输出结果 */
if (isset($_GET["callback"])) {
    echo $_GET["callback"] . '(' . $result . ')';
} else {
    echo $result;
}