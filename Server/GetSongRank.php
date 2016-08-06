<?
    header('Content-Type: application/json');

    include '../db_conn.php';

    $songId = $_REQUEST['songId'];
    
    if($songId == ""){
        echo sprintf('{"result":"0","message":"param error"}');
        exit;
        return;
    }
    
    $sql = "SELECT songId, count(*) as NUM FROM `koraoke_score` GROUP By songId ORDER BY `NUM` DESC";
    $result = mysql_query($sql);

    if(!result){
     echo sprintf('{"result":"0","message":"%s"}',"null error");

    }

    $rank = 1;
    while($row = mysql_fetch_array($result)){
            if($songId == $row['songId']){
                  echo sprintf('{"result":"1","message":"%d"}',$rank);
                  return;
                  exit;
            }
            $rank++;
    }

     echo sprintf('{"result":"0","message":"%s"}',"null error");


?>