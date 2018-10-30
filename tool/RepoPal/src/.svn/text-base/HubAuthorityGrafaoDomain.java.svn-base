//import br.ufrj.cos.bri.SearchEngine.Result;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import br.ufrj.cos.GraphInstance;
import br.ufrj.cos.bean.Document;
import br.ufrj.cos.db.HibernateDAO;
import br.ufrj.cos.foxset.search.GoogleSearch;
import br.ufrj.cos.foxset.search.SearchEngine;
import br.ufrj.cos.foxset.search.SearchException;
import br.ufrj.cos.foxset.search.WebDocument;
import br.ufrj.cos.foxset.search.YahooSearch;
import br.ufrj.cos.foxset.search.SearchEngine.Result;
import edu.uci.ics.jung.algorithms.importance.HITS;
import edu.uci.ics.jung.algorithms.importance.NodeRanking;
import edu.uci.ics.jung.graph.Graph;

public class HubAuthorityGrafaoDomain {

	public static int qtdPag = 350;
	public static int qtdMaxBackLinks = 50;
	public static int qtdLinks = 50;
	public static int qtdLevels = 1;
	public static boolean discardSameDomain = true;

	private static Connection connIreval, connFoxset;
	private static PreparedStatement psSelect, psUpdate;
	private static PrintWriter pwResultado;
	private static int indexDomains = 0;
	private static Map<String, Integer> domains = new HashMap<String, Integer>();
	private static Map<String, String> urls = new HashMap<String, String>();
	private static Map<String, Integer> docs = new HashMap<String, Integer>();
	private static Set<String> lines = new HashSet<String>();
	private static int idMax = 0;

	public static Set<Result> getBackLinks(String url, int maxBackLinks,
			boolean discardSameDomain) throws SearchException, IOException {
		int engine = 1;
		SearchEngine se = null;
		if (engine == 0) {
			se = new GoogleSearch();
			se.setAppID("F4ZdLRNQFHKUvggiU+9+60sA8vc3fohb");
		} else if (engine == 1) {
			se = new YahooSearch();
			se
					.setAppID("j3ANBxbV34FKDH_U3kGw0Jwj5Zbc__TDAYAzRopuJMGa8WBt0mtZlj4n1odUtMR8hco-");
		}
		se.setMaxResults(maxBackLinks);
		List<Result> results = se.search("link:" + url);
		Set<Result> setURLS = new HashSet<Result>();
		for (Result result : results) {
			if (!WebDocument
					.discardUrl(result.getURL(), discardSameDomain, url))
				setURLS.add(result);
		}
		return setURLS;
	}

	public static void getLinks(String url, int nivel, int max) {
		if (nivel > max) {
			return;
		}
		try {
			Integer id = domains.get(urls.get(url));
			WebDocument wf = new WebDocument(url);
			Map<String, Integer> fl = wf.getForwardLinks();
			System.out.println("Rec. " + nivel + ", FL = " + fl.size() + ": "
					+ id + " - " + url);
			int i = 0;
			for (String filhoStr : fl.keySet()) {
				String filho = tratarURL(filhoStr);
				Integer idFilho = insertInSetOfDomains(filho);
				lines.add(id + " " + idFilho + " 1");
				// if (++i <= qtdLinks) {
				getLinks(filho, nivel + 1, max);
				// }
			}

			Set<Result> results = null;
			try {
				results = getBackLinks(url, qtdMaxBackLinks, discardSameDomain);
			} catch (Exception e) {
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
				results = getBackLinks(url, qtdMaxBackLinks, discardSameDomain);
			}
			System.out.println("Rec. " + nivel + ", BL = " + results.size()
					+ ": " + id + " - " + url);
			int j = 0;
			for (Result r : results) {
				// if (++j > 2)
				// break;
				String pai = tratarURL(r.getURL());
				Integer idPai = insertInSetOfDomains(pai);
				lines.add(idPai + " " + id + " 1");
				// if (++j <= qtdLinks) {
				getLinks(pai, nivel + 1, max);
				// }
			}
			wf = null;
			System.gc();
		} catch (Exception e) {
			System.out.println("Exceção na recursão");
			System.out.println("URL: " + url);
			System.out.println("Nivel: " + nivel);
			e.printStackTrace();
		}
	}

	private static String tratarURL(String url) {
		String newUrl = url.toLowerCase();
		if (newUrl.endsWith("/")) {
			newUrl = newUrl.substring(0, newUrl.length() - 1);
		}
		try {
			atualizarURL(url, newUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newUrl;
	}

	private static void atualizarURL(String url, String newUrl)
			throws Exception {
		HibernateDAO dao = HibernateDAO.getInstance();
		dao.openSession();

		Connection cIreval = DriverManager
				.getConnection("jdbc:mysql://localhost/ireval?user=foxset&password=xamusko");

		PreparedStatement pUpdate = cIreval.prepareStatement("UPDATE document "
				+ "SET url = ? WHERE url = ?");

		List<Document> documents = (List<Document>) dao.loadByField(
				Document.class, "url", url);
		for (Document document : documents) {

			try {
				document.setUrl(newUrl);
				dao.initTransaction();
				dao.getSession().flush();
				dao.getSession().clear();
				dao.getSession().update(document);
				dao.commitTransaction();
				pUpdate.setString(1, newUrl);
				pUpdate.setString(2, url); // Reputation
				pUpdate.executeUpdate();
			} catch (Exception e) {
				dao.rollbackTransaction();
				throw e;
			}

		}

		if (dao.getSession().isOpen())
			dao.closeSession();
	}

	public static void pajek() throws Exception {
		PrintWriter writer = new PrintWriter(new FileWriter("pajek.txt"));
		writer.println("*Vertices " + (domains.keySet().size() + 2));
		for (String domain : domains.keySet()) {
			Integer id = domains.get(domain);
			writer.println(id + " \"" + domain + "\"");
		}
		writer.println("*Arcs");
		for (String linha : lines) {
			writer.println(linha);
		}
		writer.close();
	}

	public static double getScore(HITS hits, String url) {
		List rankings = hits.getRankings();
		int id = domains.get(urls.get(url));
		for (Object obj : rankings) {
			NodeRanking ranking = (NodeRanking) obj;
			if (ranking.originalPos == id) {
				return (Double.isNaN(ranking.rankScore) ? 0 : ranking.rankScore);
			}
		}
		System.out.println("ZERO!");
		return 0;
	}

	public static void jung() throws Exception {
		// if (urls == null || urls.size() == 0)
		// carregarUrls();
		GraphInstance graphInstance = new GraphInstance();
		Graph graph = graphInstance.load("pajek.txt");
		HITS hitsAuthority = new HITS(graph, true);
		hitsAuthority.evaluate();
		HITS hitsHub = new HITS(graph, false);
		hitsHub.evaluate();
		for (String url : urls.keySet()) {
			System.out.println(String.format("DOMAIN: %s", urls.get(url)));
			double authority = getScore(hitsAuthority, url);
			System.out.println(String.format("Authority (%s):\n%.30f", url,
					authority));
			double hub = getScore(hitsHub, url);
			System.out.println(String.format("Hub (%s):\n%.30f", url, hub));
			pwResultado.println(authority + " " + hub + " " + url);
			pwResultado.flush();
			if (connFoxset == null) {
				connFoxset = DriverManager
						.getConnection("jdbc:mysql://localhost/foxset?user=foxset&password=xamusko");
				psSelect = connFoxset
						.prepareStatement("SELECT id FROM document "
								+ "WHERE dataset_id = 578 AND url = ?");
				psUpdate = connFoxset
						.prepareStatement("UPDATE document_quality_dimension "
								+ "SET score = ? WHERE document_id = ? AND quality_dimension_id = ?");
			}
			psSelect.setString(1, url);
			ResultSet rs = psSelect.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("id");
				System.out.println("ID no FoxseT: " + id);
				System.out.println(String
						.format("Authority : %.50f", authority));
				psUpdate.setDouble(1, authority);
				psUpdate.setInt(2, id);
				psUpdate.setInt(3, 79); // Reputation

				int rows = psUpdate.executeUpdate();
				System.out.println(String.format("Hub : %.50f", hub));
				psUpdate.setDouble(1, hub);
				psUpdate.setInt(2, id);
				psUpdate.setInt(3, 81); // completeness

				rows += psUpdate.executeUpdate();
				System.out.println("Rows afetadas pelos 2 updates: " + rows);
			} else {
				System.out.println("Nao ha URL no Foxset: " + url);
			}
		}
	}

	private static void carregarUrls() throws IOException {
		urls = new HashMap<String, String>();
		docs = new HashMap<String, Integer>();

		FileReader fReader = new FileReader(new File("pajek.txt"));
		BufferedReader bReader = new BufferedReader(fReader);
		String line = bReader.readLine();
		while (line != null) {
			if (!line.startsWith("*Vertices")) {
				if (line.equals("*Arcs"))
					break;
				String[] words = line.split(" ");
				int id = Integer.parseInt(words[0]);
				String url = words[1].substring(1, words[1].length() - 1);
				docs.put(url, id);
				urls.put(url, getDomain(url));
			}
			line = bReader.readLine();
		}
	}

	private static String getDomain(String urlStr) throws MalformedURLException {
		String domain = null;
		URL url = new URL(urlStr);
		domain = url.getHost().toLowerCase();
		return domain;
	}

	public static void main(String[] args) throws Exception {
		pwResultado = new PrintWriter(new FileWriter("resultado.txt"));
		File objetos = null;
		// se jah tiver o arquivo pajek.txt atribuir
		// valor true, atribuir false caso contrário

		Class.forName("com.mysql.jdbc.Driver").newInstance();
		connIreval = DriverManager
				.getConnection("jdbc:mysql://localhost/ireval?user=foxset&password=xamusko");
		Statement connStat = connIreval.createStatement();

		connStat.setMaxRows(qtdPag);
		ResultSet rs = connStat
				.executeQuery("SELECT d.id, d.url FROM document AS d "
						+ "WHERE d.experiment_id = 1 AND "
						+ "((SELECT COUNT(DISTINCT evaluator_id) FROM document_evaluation AS de "
						+ "WHERE de.document_id = d.id AND de.linguistic_term_id IS NOT NULL) > 0)");
		int i = 0, iAnt = -1;
		objetos = new File("objetos.bin");
		if (objetos.exists()) {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
					objetos));
			iAnt = ois.readInt();
			idMax = ois.readInt();
			indexDomains = ois.readInt();
			domains = (Map<String, Integer>) ois.readObject();
			urls = (Map<String, String>) ois.readObject();			
			docs = (Map<String, Integer>) ois.readObject();
			idMax = new TreeSet<Integer>(docs.values()).last();
			lines = (Set<String>) ois.readObject();
			ois.close();
		}

		while (rs.next()) {
			System.out.println("i = " + ++i);
			if (i <= iAnt) {
				continue;
			}
			Integer idAtual = rs.getInt("id");
			String urlAtual = tratarURL(rs.getString("url"));
			System.out.println("Select: " + idAtual + " - " + urlAtual);
			docs.put(urlAtual, ++idMax);
			urls.put(urlAtual, getDomain(urlAtual));
			insertInSetOfDomains(urlAtual);
			getLinks(urlAtual, 1, qtdLevels);
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(objetos));
			oos.writeInt(i);
			oos.writeInt(idMax);
			oos.writeInt(indexDomains);
			oos.writeObject(domains);			
			oos.writeObject(urls);
			oos.writeObject(docs);
			oos.writeObject(lines);
			oos.close();
		}
		pajek();

		jung();
		pwResultado.close();

	}

	private static int insertInSetOfDomains(String url)
			throws MalformedURLException {
		String domain = urls.get(url);
		if (domain == null)
			domain = getDomain(url);
		Integer iDomain = domains.get(domain);
		if (iDomain == null) {
			domains.put(domain, ++indexDomains);
			iDomain = domains.get(domain);
		}
		return iDomain.intValue();
	}
}
