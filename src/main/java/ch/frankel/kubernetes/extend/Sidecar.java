package ch.frankel.kubernetes.extend;

import io.kubernetes.client.informer.SharedIndexInformer;
import io.kubernetes.client.informer.SharedInformerFactory;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.util.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sidecar {

    private static final Logger LOGGER = LoggerFactory.getLogger(Sidecar.class);

    public static void main(String[] args) throws Exception {
        LOGGER.info("*** JVM Operator v1.7 ***");
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        print();
    }

    private static void print() {
        CoreV1Api api = new CoreV1Api();
        SharedInformerFactory factory = new SharedInformerFactory();
        SharedIndexInformer<V1Pod> informer = factory.sharedIndexInformerFor(
                it -> api.listPodForAllNamespacesCall(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        it.resourceVersion,
                        it.timeoutSeconds,
                        it.watch,
                        null),
                V1Pod.class,
                V1PodList.class);
        informer.addEventHandler(new SidecarEventHandler());
        factory.startAllRegisteredInformers();
    }
}