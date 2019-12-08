package ch.frankel.kubernetes.extend;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.util.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListPods {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListPods.class);

    public static void main(String[] args) throws Exception {
        LOGGER.info("*** JVM Operator v1.0 ***");
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        CoreV1Api core = new CoreV1Api();
        V1PodList pods = core.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null);
        pods.getItems()
                .stream()
                .map(it -> "- " + it.getMetadata().getName())
                .forEach(System.out::println);
    }
}
