<?
    header('Content-Type: application/json');

    include '../db_conn.php';
    
    $songId = $_REQUEST['songId'];

    if($songId == ""){
        echo sprintf('{"result":"0","message":"param error"}');
        exit;
        return;
    }

    $sql = "SELECT COUNT(*) FROM `koraoke_score` WHERE `songId` = '$songId';";
    $result = mysql_query($sql);
    if(!$result){
        echo sprintf('{"result":"0","message":"db_error"}');
        exit;
        return;
    }

    $row = mysql_fetch_array($result);

    echo sprintf('{"result":"1","message":"%d"}',$row[0]);


?>