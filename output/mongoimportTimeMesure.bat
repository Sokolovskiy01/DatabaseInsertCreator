cd "C:\Program Files\MongoDB\Server\4.4\bin"
echo %time%
mongoimport --db bachelorTest --collection managers < C:\Users\Sokol\Desktop\DatabaseInsertCreator\output\testJson.json
echo %time%
pause