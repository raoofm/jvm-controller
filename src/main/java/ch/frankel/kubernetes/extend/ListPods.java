package ch.frankel.kubernetes.extend;

import com.google.gson.reflect.TypeToken;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Node;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.Watch;
import okhttp3.Call;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.stream.StreamSupport;

public class ListPods {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListPods.class);

    public static void main(String[] args) throws Exception {
        LOGGER.info("*** JVM Operator v1.3 ***");
        ApiClient client = Config.defaultClient().setReadTimeout(0);
        Configuration.setDefaultApiClient(client);
        print(client);
    }

    private static void print(ApiClient client) throws ApiException, IOException {
        CoreV1Api api = new CoreV1Api();
        Call call = api.listPodForAllNamespacesCall(null, null, null, null, null, null, null, null, Boolean.TRUE, null);
        Type type = new TypeToken<Watch.Response<V1Node>>() {
        }.getType();
        try (Watch<V1Node> watch = Watch.createWatch(client, call, type)) {
            StreamSupport.stream(watch.spliterator(), false)
                    .map(it -> it.type + ": " + it.object.getMetadata().getName())
                    .forEach(LOGGER::info);
        }
    }
}