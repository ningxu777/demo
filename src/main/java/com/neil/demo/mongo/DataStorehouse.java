package com.neil.demo.mongo;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Neil on 2018/3/4.
 */
public class DataStorehouse {

    /// <summary>
    /// mongodb服务
    /// </summary>
    static MongoService _mongoService;

    /// <summary>
    /// mongo集合缓存
    /// </summary>
//    static IDictionary<string, IMongoCollection<BsonDocument>> _mongoCollection;

    /// <summary>
    /// The asynchronous
    /// </summary>
//    static readonly object async = new object();

    /// <summary>
    /// Mongo集合名称
    /// </summary>
    /// <value>
    /// The name of the collection.
    /// </value>
    String CollectionName;

    /// <summary>
    /// Initializes the <see cref="DataStorehouse"/> class.
    /// </summary>
//    static DataStorehouse()
//    {
//        _mongoService = new MongoService();
//        _mongoCollection = new Dictionary<string, IMongoCollection<BsonDocument>>();
//
//        //创建一个线程，用于维持azure平台的mongodb链接   modify by 苏宏伟   2015/10/13
//        Thread thread1 = new Thread(new ThreadStart(KeepMongodbConn));
//
//        //启动此线程
//        thread1.Start();
//    }

    /// <summary>
    /// Initializes a new instance of the <see cref="DataStorehouse" /> class.
    /// </summary>
    /// <param name="collectionName">Name of the collection.</param>
    DataStorehouse(String collectionName)
    {
        this.CollectionName = collectionName;
    }

    /// <summary>
    /// Keeps the mongodb connection.
    /// </summary>
//    private static void KeepMongodbConn()
//    {
//        while (true)
//        {
//            try
//            {
//                var database = _mongoService.getDatabase("KeepConn");
//                var collection = database.GetCollection<BsonDocument>("Data");
//
//                var obj = new
//                {
//                    dateTime = DateTime.Now.ToString()
//                };
//
//                var doc = obj.ToBsonDocument();
//
//                collection.InsertOneAsync(doc);
//            }
//            catch (Exception ex)
//            {
//                System.Diagnostics.Trace.TraceWarning("做心跳请求：" + ex.Message);
//            }
//
//            Thread.Sleep(20000);
//        }
//    }


    /// <summary>
    /// Storages the specified project identifier.
    /// </summary>
    /// <param name="projectId">The project identifier.</param>
    /// <param name="dataGuid">The data unique identifier.</param>
    /// <param name="json_xml">The json_xml.</param>
    void Storage(int projectId, String dataGuid, String json)
    {
        this.Storage(projectId, 0, dataGuid, json, "",true);
    }

    /// <summary>
    /// 数据的JSON格式存储
    /// </summary>
    /// <param name="projectId">任务Id.</param>
    /// <param name="personInProjectId">任务领取记录Id.</param>
    /// <param name="dataGuid">数据唯一标识.</param>
    /// <param name="json_xml">JSON内容.</param>
    void Storage(Integer projectId, Integer personInProjectId, String dataGuid, String json, String fileUrl, Boolean imports)
    {

        imports = imports==null?false:imports;
        //获取集合
        MongoCollection<Document> collection = this.GetCollection(projectId, this.CollectionName);

        //构造新的JSON内容
        String newJson = json;

        //追加 其他属性
        if (json != null && json.startsWith("["))
            newJson = "{\"array\":" + json + "}";

        Document document = null;

        if (StringUtils.isEmpty(newJson))
            document = new Document();
        else
//            document = BsonSerializer.Deserialize<BsonDocument>(newJson);
            document = Document.parse(newJson);
        //追加属性
        if (!document.containsKey("_personInProjectId"))
        document.append("_personInProjectId", personInProjectId);

        if (!document.containsKey("_createTime"))
        document.append("_createTime", new Timestamp(new Date().getTime()).toString());

        if (!document.containsKey("_guid"))
        document.append("_guid", dataGuid.toLowerCase());



        //追加文件URL
        if (!document.containsKey("_fileUrl"))
        {
            if (fileUrl != null && (fileUrl.length() > 0 && fileUrl.indexOf("http") > -1 || fileUrl.indexOf("//") > -1))
                document.append("_fileUrl", fileUrl);
        }


        //进行5次重试
//        var policy = Policy
//                .Handle<Exception>()
//                .WaitAndRetry(5, retryAttempt => TimeSpan.FromSeconds(Math.Pow(2, retryAttempt)), (ex, timeSpan) =>
//        {
//            Trace.TraceError(string.Format("保存JSON出错，重试：\n{0}", ex.ToString()));
//        }).ExecuteAndCapture(() =>
//            {
        //var bson = collection.FindOneAndReplaceAsync(filter, document, new FindOneAndReplaceOptions<BsonDocument, BsonDocument>()
        //{
        //    IsUpsert = true
        //}).ConfigureAwait(false);
        if (imports)
        {
            document.append("_id", new ObjectId());

//            collection.InsertOneAsync(document).Wait();
            collection.insertOne(document);
        }
        else

        {
            Bson filter1 = Filters.eq("_guid", dataGuid.toLowerCase());

            Document document1 = collection.find(filter1).first();
            if(document1 == null ){
                document.append("_id", new ObjectId());

//            collection.InsertOneAsync(document).Wait();
                collection.insertOne(document);
            }else{
                document.append("_id", (ObjectId)document1.get("_id"));
//            var bson = collection.FindOneAndReplaceAsync(filter, document, new FindOneAndReplaceOptions<BsonDocument, BsonDocument>()
//            {
//                IsUpsert = true
//            }).Result;
                Bson filter = Filters.eq("_guid", dataGuid.toLowerCase());

                collection.findOneAndReplace(filter,document);
            }

        }
//        });

        //最终实在保存失败，则记录到日志
//        if (policy.FinalException != null)
//            Trace.TraceWarning("保存JSON至MongoDB 异常\n{0}", policy.FinalException.ToString());

    }

    /// <summary>
    /// 根据数据标识，获取数据的JSON
    /// </summary>
    /// <param name="projectId">任务Id.</param>
    /// <param name="dataGuid">数据唯一标识.</param>
    /// <returns>JSON内容字符串</returns>
    public String Get(Integer projectId, String dataGuid)
    {
        String[] removes = new String[] { "_id" };
        dataGuid = dataGuid.toLowerCase();
        MongoCollection<Document> collection = this.GetCollection(projectId, this.CollectionName);
        Bson filter = Filters.eq("_guid",dataGuid);
        FindIterable<Document> list = collection.find(filter);
//        var filter = Builders<BsonDocument>.Filter.Eq("_guid", dataGuid.ToString());
//        var list = collection.Find<BsonDocument>(filter);

        Document result = new Document();
        if (list != null)
        {
            result = list.first();
        }
        else
        {
            filter = Filters.eq("_id", new ObjectId(dataGuid));
            list = collection.find(filter);

            if (list != null)
            {
                result = list.first();
            }
        }

        if (result == null)
            return "{}";

        //删除特殊属性
//        foreach (var item in removes)
//        {
//            if (result.Contains(item))
//                result.Remove(item);
//        }
        result.remove("_id");
        JsonWriterSettings settings = new JsonWriterSettings();
        String json = result.toJson();
//        String json_xml = result.toJson(new MongoDB.Bson.IO.JsonWriterSettings()
//        {
//            OutputMode = MongoDB.Bson.IO.JsonOutputMode.Shell,
//            GuidRepresentation = GuidRepresentation.Unspecified
//        });

        return json;
    }

    /// <summary>
    /// Gets the database information.
    /// </summary>
    /// <param name="projectId">The project identifier.</param>
    /// <returns></returns>
    public static String GetDBInfo(int projectId)
    {
        //获取数据库
//        var dataBase = _mongoService.Client.GetDatabase(projectId.ToString());
        MongoDatabase dataBase = _mongoService.getDatabase(String.valueOf(projectId));

        if (dataBase == null)
            return "";

//        var command = @"{ dbStats: 1, scale: 1024 }";
        Map<String,Object> map = new HashMap<String, Object>();
//        map.put("scale",1024);
        map.put("dbStats",projectId);
        Bson command = new Document(map);
        Document doc = dataBase.runCommand(command);

//        var doc = dataBase.RunCommandAsync<BsonDocument>(command).Result;
//        Boolean Indent = true;
        String NewLineChars = "\n";
        JsonMode outputMode = JsonMode.STRICT;
        String indentCharacters = "    ";
        JsonWriterSettings jsonWriterSettings = new JsonWriterSettings(outputMode,indentCharacters,NewLineChars);

        return doc.toJson(jsonWriterSettings);
//        return doc.ToJson(new MongoDB.Bson.IO.JsonWriterSettings()
//        {
//            Indent = true,
//            NewLineChars = "\n",
//            OutputMode = MongoDB.Bson.IO.JsonOutputMode.Strict
//        });
    }

    /// <summary>
    /// Gets the jsons.
    /// </summary>
    /// <param name="projectId">The project identifier.</param>
    /// <param name="identifications">The identifications.</param>
    /// <returns></returns>
//    internal async Task<List<string>> GetJsons(int projectId, IList<Guid> identifications)
//    {
//        string[] removes = new string[] { "_id", "_createTime", "_personInProjectId" };
//        List<string> list = new List<string>();
//        var collection = this.GetCollection(projectId, this.CollectionName);
//
//        List<BsonValue> listKey = new List<BsonValue>();
//        foreach (var g in identifications)
//        {
//            listKey.Add(g.ToString());
//        }
//
//        var filter = Builders<BsonDocument>.Filter.In("_guid", listKey);
//        var result = collection.Find(filter);
//
//        await result.ForEachAsync(item =>
//            {
//                    //删除特殊属性
//                    foreach (var r in removes)
//                    {
//        if (item.Contains(r))
//            item.Remove(r);
//        }
//
//        list.Add(item.ToJson());
//        });
//
//        return list;
//    }

    /// <summary>
    /// Gets the jsons.
    /// </summary>
    /// <param name="taskId">The task identifier.</param>
    /// <param name="takeCount">The take count.</param>
    /// <returns></returns>
    public String GetJsons(int taskId, int takeCount)
    {
        MongoCollection<Document> collection = this.GetCollection(taskId, this.CollectionName);

//        Document result = collection.Find(new BsonDocument()).Limit(takeCount).ToListAsync().Result;
//        Bson bson = Filters.size("_id",takeCount);
        FindIterable<Document> result = collection.find().limit(takeCount);
        MongoCursor<Document> iterator =  result.iterator();
        JsonWriterSettings jsonWriterSettings = new JsonWriterSettings(true);
        StringBuilder resultJson = new StringBuilder("[");
        while(iterator.hasNext()){
            Document document = iterator.next();
            String json = document.toJson(jsonWriterSettings);
            resultJson.append(json);
            resultJson.append(",");
        }
        if(resultJson.lastIndexOf(",") > -1){
            resultJson.delete(resultJson.lastIndexOf(","),resultJson.lastIndexOf(","));
        }
        resultJson.append("]");
        return resultJson.toString();
    }

    /// <summary>
    /// 获取数据json集合
    /// </summary>
    /// <param name="projectId"></param>
    /// <param name="identifications"></param>
    /// <returns></returns>
    public Map<String, String> Get(int projectId, List<String> identifications)
    {
        Map<String, String> dictResult = new ConcurrentHashMap<String, String>();
        String[] removes = new String[] { "_id", "_createTime", "_personInProjectId" };

//        Map<ObjectId, String> keyValue = new HashMap<ObjectId, String>();
        Map<String, String> keyValue = new HashMap<String, String>();
//        List<ObjectId> listKey = new ArrayList<ObjectId>();
        List<String> listKey = new ArrayList<String>();

//        for (String g:identifications)
//        {
//            ObjectId objId = new ObjectId(g);
//
//            if (!listKey.contains(objId))
//            {
//                listKey.add(objId);
//                keyValue.put(objId, g);
//            }
//        }
//
//        Bson filter = Filters.in("_id", listKey);

        for (String g:identifications)
        {
//            ObjectId objId = new ObjectId(g);

            if (!listKey.contains(g.toLowerCase()))
            {
                listKey.add(g.toLowerCase());
                keyValue.put(g.toLowerCase(), g);
            }
        }

        Bson filter = Filters.in("_guid", listKey);
        MongoCollection collection = this.GetCollection(projectId, this.CollectionName);
        MongoCursor<Document> result = collection.find(filter).iterator();
        while (result.hasNext())
        {
            Document item = result.next();
            //删除特殊属性
            for (String i:removes)
            {
                if (item.containsKey(i))
                    item.remove(i);
            }
            JsonWriterSettings jsonWriterSettings = new JsonWriterSettings(JsonMode.SHELL);
            String json = item.toJson(jsonWriterSettings);
//            String json_xml = item.toJson(new MongoDB.Bson.IO.JsonWriterSettings()
//            {
//                OutputMode = MongoDB.Bson.IO.JsonOutputMode.Shell,
//                GuidRepresentation = GuidRepresentation.Unspecified
//            });

            if (item.containsKey("_guid"))
            {
//                ObjectId key = new ObjectId(item.getString("_guid"));
                String key = item.getString("_guid");

                if (keyValue.containsKey(key))
                {
                    dictResult.put(keyValue.get(key), json);
                }
            }
        }

        return dictResult;
    }


    /// <summary>
    /// Gets the collection.
    /// </summary>
    /// <param name="projectId">The project identifier.</param>
    /// <param name="name">The name.</param>
    /// <returns></returns>
    /// <exception cref="System.NullReferenceException">mongoService is null</exception>
    private static MongoCollection<Document> GetCollection(Integer projectId, String name)
    {
//        if (_mongoService == null)
//            throw new NullReferenceException("mongoService is null");

        MongoCollection<Document> mongoCollection = null;

        {

            MongoDatabase database = _mongoService.getDatabase(String.valueOf(projectId));

            mongoCollection = database.getCollection(name);


//            mongoCollection = database.GetCollection<BsonDocument>(name);

//            _mongoCollection.Add(key, mongoCollection);
        }

        return mongoCollection;
    }


}
