<?php
    header('Content-Type: application/json');

    include '../db_conn.php';

    $userId = $_POST['userId'];
    $songId = $_POST['songId'];
    $score = $_POST['score'];

    $sql = "INSERT INTO `koraoke_score`(`scoreId`, `userId`, `songId`, `score`, `addDate`)
             VALUES (Null,$userId,$songId,$score,Null)";

    $result = mysql_query($sql);

    if(!$result){
        echo sprintf('{"result":"0","message":"db_error"}');
        exit;
        return;
    }    

    echo sprintf('{"result":"1","message":"success"}');          


?>