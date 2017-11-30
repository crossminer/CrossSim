//import br.ufrj.cos.bri.SearchEngine.Result;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pt.tumba.links.WebGraph;
import br.ufrj.cos.GraphInstance;
import br.ufrj.cos.foxset.search.GoogleSearch;
import br.ufrj.cos.foxset.search.SearchEngine;
import br.ufrj.cos.foxset.search.SearchException;
import br.ufrj.cos.foxset.search.WebDocument;
import br.ufrj.cos.foxset.search.YahooSearch;
import br.ufrj.cos.foxset.search.SearchEngine.Result;
import edu.uci.ics.jung.algorithms.importance.HITS;
import edu.uci.ics.jung.algorithms.importance.NodeRanking;
import edu.uci.ics.jung.graph.Graph;

public class HubAuthorityGrafao {

	public static int qtdPag = 350;
	public static int qtdMaxBackLinks = 50;
	public static int qtdLinks = 50;
	public static int qtdLevels = 1;
	public static boolean discardSameDomain = true, repeatZeros = true,
			useJung = true, onlyHITS = false;
	public static String usuario = "foxset", senha = "xamusko";

	private static Connection connIreval, connFoxset, connPajek;
	private static PreparedStatement psFoxsetExisteURL,
			psFoxsetAtualizarScores, psPajekExisteURL, psPajekInserirURL,
			psPajekInserirLink, psPajekAtualizarURL, psPajekAtualizarScores;
	private static PrintWriter pwResultado;
	private static Set<String> urls = new HashSet<String>();
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

	public static void getLinks(int idPajek, String url, int nivel, int max) {
		if (nivel > max) {
			return;
		}
		try {
			String host = new URL(url).getHost();
			WebDocument wf = new WebDocument(url);
			Map<String, Integer> fl = wf.getForwardLinks();
			int forwardLinks = fl.keySet().size();
			System.out.println("Rec. " + nivel + ", FL = " + fl.size() + ": "
					+ idPajek + " - " + url);
			int i = 0;
			for (String filhoStr : fl.keySet()) {
				String filho = WebDocument.tratarURL(filhoStr);
				if (WebDocument.discardURLSameDomain(filho, discardSameDomain,
						url)) {
					continue;
				}
				// Checa se existe nas urls
				psPajekExisteURL.setString(1, filho);
				ResultSet rsPajek = psPajekExisteURL.executeQuery();
				if (!rsPajek.next()) {
					psPajekInserirURL.setString(1, filho);
					psPajekInserirURL.setNull(2, Types.INTEGER);
					psPajekInserirURL.setNull(3, Types.INTEGER);
					psPajekInserirURL.executeUpdate();
					rsPajek = psPajekExisteURL.executeQuery();
					rsPajek.next();
				}
				int idFilho = rsPajek.getInt("pajek_id");
				try {
					psPajekInserirLink.setInt(1, idPajek);
					psPajekInserirLink.setInt(2, idFilho);
					psPajekInserirLink.executeUpdate();
				} catch (Exception e) {
				}
				// if (++i <= qtdLinks) {
				getLinks(idFilho, filho, nivel + 1, max);
				// }
			}

			Set<Result> results = getBackLinks(url, qtdMaxBackLinks,
					discardSameDomain);
			int backLinks = results.size();
			System.out.println("Rec. " + nivel + ", BL = " + results.size()
					+ ": " + idPajek + " - " + url);
			int j = 0;
			for (Result r : results) {
				// if (++j > 2)
				// break;
				String pai = WebDocument.tratarURL(r.getURL());
				if (WebDocument.discardURLSameDomain(pai, discardSameDomain,
						url)) {
					continue;
				}
				// Checa se existe nas urls
				psPajekExisteURL.setString(1, pai);
				ResultSet rsPajek = psPajekExisteURL.executeQuery();
				if (!rsPajek.next()) {
					psPajekInserirURL.setString(1, pai);
					psPajekInserirURL.setNull(2, Types.INTEGER);
					psPajekInserirURL.setNull(3, Types.INTEGER);
					psPajekInserirURL.executeUpdate();
					rsPajek = psPajekExisteURL.executeQuery();
					rsPajek.next();
				}
				int idPai = rsPajek.getInt("pajek_id");
				try {
					psPajekInserirLink.setInt(1, idPai);
					psPajekInserirLink.setInt(2, idPajek);
					psPajekInserirLink.executeUpdate();
				} catch (Exception e) {
				}

				// if (++j <= qtdLinks) {
				getLinks(idPai, pai, nivel + 1, max);
				// }
			}
			psPajekAtualizarURL.setInt(1, backLinks);
			psPajekAtualizarURL.setInt(2, forwardLinks);
			psPajekAtualizarURL.setTimestamp(3, new Timestamp(new Date()
					.getTime()));
			psPajekAtualizarURL.setInt(4, idPajek);
			psPajekAtualizarURL.executeUpdate();
			wf = null;
			System.gc();
		} catch (Exception e) {
			System.out.println("Exceção na recursão");
			System.out.println("URL: " + url);
			System.out.println("Nivel: " + nivel);
			e.printStackTrace();
		}
	}

	public static void pajek() throws Exception {
		PrintWriter writer = new PrintWriter(new FileWriter("pajek.txt"));
		ResultSet rs = connPajek.createStatement().executeQuery(
				"SELECT pajek_id FROM urls");
		Set<Integer> ids = new HashSet<Integer>();
		while (rs.next()) {
			ids.add(rs.getInt("pajek_id"));
		}
		writer.println("*Vertices " + ids.size());
		for (Integer id : ids) {
			writer.println(id + " \"" + id + "\"");
		}
		writer.println("*Arcs");
		rs = connPajek.createStatement().executeQuery(
				"SELECT from_pajek_id, to_pajek_id FROM links");
		while (rs.next()) {
			writer.println(rs.getInt("from_pajek_id") + " "
					+ rs.getInt("to_pajek_id") + " 1");
		}
		writer.close();
	}

	public static double getScore(HITS hits, int idPajek) {
		List rankings = hits.getRankings();
		for (Object obj : rankings) {
			NodeRanking ranking = (NodeRanking) obj;
			if (ranking.originalPos == idPajek) {
				return (Double.isNaN(ranking.rankScore) ? 0 : ranking.rankScore);
			}
		}
		System.out.println("ZERO!");
		return 0;
	}

	public static void jung() throws Exception {
		GraphInstance graphInstance = new GraphInstance();
		Graph graph = graphInstance.load("pajek.txt");
		HITS hitsAuthority = new HITS(graph, true);
		hitsAuthority.evaluate();
		HITS hitsHub = new HITS(graph, false);
		hitsHub.evaluate();
		ResultSet rs = connPajek.createStatement().executeQuery(
				"SELECT pajek_id, foxset_id, url FROM urls");
		while (rs.next()) {
			int idPajek = rs.getInt("pajek_id");
			int idFoxset = rs.getInt("foxset_id");
			String url = rs.getString("url");
			double authority = getScore(hitsAuthority, idPajek);
			System.out.println(String.format("Authority (%s):\n%.30f", url,
					authority));
			double hub = getScore(hitsHub, idPajek);
			System.out.println(String.format("Hub (%s):\n%.30f", url, hub));
			psPajekAtualizarScores.setDouble(1, authority);
			psPajekAtualizarScores.setDouble(2, hub);
			psPajekAtualizarScores.setInt(3, idPajek);
			psPajekAtualizarScores.executeUpdate();
			if (idFoxset > 0) {
				System.out.println("ID no FoxseT: " + idFoxset);
				System.out.println(String
						.format("Authority : %.50f", authority));
				psFoxsetAtualizarScores.setDouble(1, authority);
				psFoxsetAtualizarScores.setInt(2, idFoxset);
				psFoxsetAtualizarScores.setInt(3, 79); // Reputation

				int rows = psFoxsetAtualizarScores.executeUpdate();
				System.out.println(String.format("Hub : %.50f", hub));
				psFoxsetAtualizarScores.setDouble(1, hub);
				psFoxsetAtualizarScores.setInt(2, idFoxset);
				psFoxsetAtualizarScores.setInt(3, 81); // completeness

				rows += psFoxsetAtualizarScores.executeUpdate();
				System.out.println("Rows afetadas pelos 2 updates: " + rows);
			} else {
				System.out.println("Nao ha URL no Foxset: " + url);
			}
		}
	}

	private static void tumba() throws Exception {
		WebGraph wg = new WebGraph();
		ResultSet rs = connPajek.createStatement().executeQuery(
				"SELECT from_pajek_id, to_pajek_id FROM links");
		while (rs.next()) {
			wg.addLink(Integer.toString(rs.getInt("from_pajek_id")), Integer
					.toString(rs.getInt("to_pajek_id")), 1.0);
		}
		pt.tumba.links.HITS hits = new pt.tumba.links.HITS(wg);
		rs = connPajek.createStatement().executeQuery(
				"SELECT pajek_id, foxset_id, url FROM urls");
		while (rs.next()) {
			int idPajek = rs.getInt("pajek_id");
			int idFoxset = rs.getInt("foxset_id");
			String url = rs.getString("url");
			double authority = hits.authorityScore(Integer.toString(idPajek));
			System.out.println(String.format("Authority (%s):\n%.30f", url,
					authority));
			double hub = hits.hubScore(Integer.toString(idPajek));
			System.out.println(String.format("Hub (%s):\n%.30f", url, hub));
			psPajekAtualizarScores.setDouble(1, authority);
			psPajekAtualizarScores.setDouble(2, hub);
			psPajekAtualizarScores.setInt(3, idPajek);
			psPajekAtualizarScores.executeUpdate();
			if (idFoxset > 0) {
				System.out.println("ID no FoxseT: " + idFoxset);
				System.out.println(String
						.format("Authority : %.50f", authority));
				psFoxsetAtualizarScores.setDouble(1, authority);
				psFoxsetAtualizarScores.setInt(2, idFoxset);
				psFoxsetAtualizarScores.setInt(3, 79); // Reputation

				int rows = psFoxsetAtualizarScores.executeUpdate();
				System.out.println(String.format("Hub : %.50f", hub));
				psFoxsetAtualizarScores.setDouble(1, hub);
				psFoxsetAtualizarScores.setInt(2, idFoxset);
				psFoxsetAtualizarScores.setInt(3, 81); // completeness

				rows += psFoxsetAtualizarScores.executeUpdate();
				System.out.println("Rows afetadas pelos 2 updates: " + rows);
			} else {
				System.out.println("Nao ha URL no Foxset: " + url);
			}
		}
	}

	private static void log(String log) {
		System.out.print(log);
	}

	private static void logln(String log) {
		System.out.println(log);
	}

	private static Connection connectDB(String db) throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://127.0.0.1/" + db
				+ "?user=" + usuario + "&password=" + senha);
	}

	public static void main(String[] args) throws Exception {
		log("Connecting DBs... ");
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		connIreval = connectDB("ireval");
		connFoxset = connectDB("foxset");
		connPajek = connectDB("pajek");
		logln("[Done]");
		psPajekExisteURL = connPajek
				.prepareStatement("SELECT pajek_id FROM urls WHERE url = ?");
		psFoxsetExisteURL = connFoxset
				.prepareStatement("SELECT id FROM document "
						+ "WHERE dataset_id = 578 AND url = ?");
		psPajekInserirURL = connPajek
				.prepareStatement("INSERT INTO urls (url, ireval_id, foxset_id) VALUES (?, ?, ?)");
		psPajekInserirLink = connPajek
				.prepareStatement("INSERT INTO links (from_pajek_id, to_pajek_id) VALUES (?, ?)");
		psPajekAtualizarURL = connPajek
				.prepareStatement("UPDATE urls SET back_links = ?, forward_links = ?, last_attempt = ?, attempts = attempts + 1 WHERE pajek_id = ?");
		psFoxsetAtualizarScores = connFoxset
				.prepareStatement("UPDATE document_quality_dimension "
						+ "SET score = ? WHERE document_id = ? AND quality_dimension_id = ?");
		psPajekAtualizarScores = connPajek
				.prepareStatement("UPDATE urls SET authority = ?, hub = ? WHERE pajek_id = ?");
		if (!onlyHITS) {
			log("Retrieving URLs from Ireval...");
			Statement connStat = connIreval.createStatement();
			connStat.setMaxRows(qtdPag);
			ResultSet rsIreval = connStat
					.executeQuery("SELECT d.id, d.url FROM document AS d "
							+ "WHERE d.experiment_id = 1 AND "
							+ "((SELECT COUNT(DISTINCT evaluator_id) FROM document_evaluation AS de "
							+ "WHERE de.document_id = d.id AND de.linguistic_term_id IS NOT NULL) > 0)");
			logln("[Done]");
			ResultSet rsPajek, rsFoxset = null;
			while (rsIreval.next()) {
				String url = rsIreval.getString("url");
				log("Checking for URL " + url + " in Pajek...");
				psPajekExisteURL.setString(1, url);
				rsPajek = psPajekExisteURL.executeQuery();
				if (!rsPajek.next()) {
					int idIreval = rsIreval.getInt("id");
					psPajekInserirURL.setString(1, WebDocument.tratarURL(url));
					psPajekInserirURL.setInt(2, idIreval);
					psFoxsetExisteURL.setString(1, url);
					rsFoxset = psFoxsetExisteURL.executeQuery();
					if (rsFoxset.next()) {
						int idFoxset = rsFoxset.getInt("id");
						psPajekInserirURL.setInt(3, idFoxset);
					} else {
						psPajekInserirURL.setNull(3, Types.INTEGER);
					}
					psPajekInserirURL.executeUpdate();
					logln("[Inserted]");
				} else {
					logln("[Already there]");
				}
			}
			if (repeatZeros) {
				log("Retrieving URLs with no back or forward links...");
				rsPajek = connPajek
						.createStatement()
						.executeQuery(
								"SELECT pajek_id, url FROM urls WHERE ireval_id IS NOT NULL AND (back_links = 0 OR forward_links = 0)");
				while (rsPajek.next()) {
					int idPajek = rsPajek.getInt("pajek_id");
					String url = rsPajek.getString("url");
					getLinks(idPajek, url, 1, qtdLevels);
				}
			}
			log("Retrieving URLs left to crawl...");
			rsPajek = connPajek
					.createStatement()
					.executeQuery(
							"SELECT pajek_id, url FROM urls WHERE ireval_id IS NOT NULL AND last_attempt IS NULL");
			while (rsPajek.next()) {
				int idPajek = rsPajek.getInt("pajek_id");
				String url = rsPajek.getString("url");
				getLinks(idPajek, url, 1, qtdLevels);
			}
		}
		if (useJung) {
			pajek();
			jung();
		} else {
			tumba();
		}
	}
}
