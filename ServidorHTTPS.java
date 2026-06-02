import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsParameters;
import com.sun.net.httpserver.HttpsServer;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;

public class ServidorHTTPS {
    private static final int PORTA = 8443;
    private static final String ARQUIVO_HTML = "index.html";
    private static final String ARQUIVO_KEYSTORE = "keystore.jks";
    private static final char[] SENHA_KEYSTORE = "senha123".toCharArray();

    public static void main(String[] args) {
        try {
            SSLContext sslContext = criarSSL();

            HttpsServer servidor = HttpsServer.create(new InetSocketAddress(PORTA), 0);
            servidor.setHttpsConfigurator(new HttpsConfigurator(sslContext) {
                @Override
                public void configure(HttpsParameters parametros) {
                    SSLParameters sslParameters = getSSLContext().getDefaultSSLParameters();
                    sslParameters.setNeedClientAuth(false);
                    parametros.setProtocols(sslParameters.getProtocols());
                    parametros.setCipherSuites(sslParameters.getCipherSuites());
                    parametros.setSSLParameters(sslParameters);
                }
            });

            servidor.createContext("/", ServidorHTTPS::responderPagina);
            servidor.setExecutor(null);
            servidor.start();

            System.out.println("===================================");
            System.out.println("Servidor HTTPS rodando!");
            System.out.println("Acesse: https://localhost:" + PORTA);
            System.out.println("===================================");
            System.out.println("Pressione Ctrl+C para parar");
        } catch (Exception e) {
            System.err.println("Erro ao iniciar servidor:");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static void responderPagina(HttpExchange exchange) throws IOException {
        try {
            if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                enviarTexto(exchange, 405, "Metodo nao permitido.");
                return;
            }

            String caminho = exchange.getRequestURI().getPath();
            if (!"/".equals(caminho) && !"/index.html".equals(caminho)) {
                enviarTexto(exchange, 404, "Pagina nao encontrada.");
                return;
            }

            String html = lerArquivo(ARQUIVO_HTML);
            byte[] resposta = html.getBytes(StandardCharsets.UTF_8);

            Headers headers = exchange.getResponseHeaders();
            headers.set("Content-Type", "text/html; charset=UTF-8");

            exchange.sendResponseHeaders(200, resposta.length);
            try (OutputStream saida = exchange.getResponseBody()) {
                saida.write(resposta);
            }
        } catch (Exception e) {
            enviarTexto(exchange, 500, "Erro ao carregar pagina: " + e.getMessage());
        }
    }

    private static SSLContext criarSSL() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("JKS");

        try (InputStream arquivo = Files.newInputStream(Paths.get(ARQUIVO_KEYSTORE))) {
            keyStore.load(arquivo, SENHA_KEYSTORE);
        }

        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm()
        );
        keyManagerFactory.init(keyStore, SENHA_KEYSTORE);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

        return sslContext;
    }

    private static String lerArquivo(String nomeArquivo) throws IOException {
        return Files.readString(Paths.get(nomeArquivo), StandardCharsets.UTF_8);
    }

    private static void enviarTexto(HttpExchange exchange, int status, String texto) throws IOException {
        byte[] resposta = texto.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
        exchange.sendResponseHeaders(status, resposta.length);

        try (OutputStream saida = exchange.getResponseBody()) {
            saida.write(resposta);
        }
    }
}
