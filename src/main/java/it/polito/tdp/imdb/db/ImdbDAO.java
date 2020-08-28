package it.polito.tdp.imdb.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Arco;
import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Movie;

public class ImdbDAO {
	
	public List<Actor> listAllActors(){
		String sql = "SELECT * FROM actors";
		List<Actor> result = new ArrayList<Actor>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Actor actor = new Actor(res.getInt("id"), res.getString("first_name"), res.getString("last_name"),
						res.getString("gender"));
				
				result.add(actor);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Movie> listAllMovies(){
		String sql = "SELECT * FROM movies";
		List<Movie> result = new ArrayList<Movie>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Movie movie = new Movie(res.getInt("id"), res.getString("name"), 
						res.getInt("year"), res.getDouble("rank"));
				
				result.add(movie);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Director> listAllDirectors(){
		String sql = "SELECT * FROM directors";
		List<Director> result = new ArrayList<Director>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Director director = new Director(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
				
				result.add(director);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Director> getVertici(int anno) {
		String sql = " SELECT d.`id` as id, d.`first_name` as name, d.`last_name`as last " + 
				" FROM movies as m, directors as d, movies_directors as md " + 
				" WHERE d.id=md.`director_id` and md.`movie_id`=m.`id` " + 
				" and m.year= ? " + 
				" GROUP BY id " + 
				" ORDER BY last ASC ";
		List<Director> result = new ArrayList<Director>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Director director = new Director(res.getInt("id"), res.getString("name"), res.getString("last"));
				
				result.add(director);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Integer> getAnno() {
		String sql = " SELECT DISTINCT m.`year` as year " + 
				" FROM movies as m " + 
				" ORDER BY year ASC ";
		List<Integer> result = new ArrayList<Integer>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
				result.add(res.getInt("year"));
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Arco> getArchi(Integer anno) {
		String sql = "  SELECT d1.`id` as id1, d1.`first_name` as name1, d1.`last_name`as last1, " + 
				" d2.`id` as id2, d2.`first_name` as name2, d2.`last_name`as last2, COUNT(DISTINCT r1.`actor_id`) as peso " + 
				" FROM movies as m1, movies as m2, directors as d1, directors as d2, movies_directors as md1, movies_directors as md2, roles as r1, roles as r2 " + 
				" WHERE d1.`id`>d2.`id` " + 
				" and m1.year= ? and m2.year= ? " + 
				" and d1.id=md1.director_id and d2.id=md2.director_id " + 
				" and m1.`id`=md1.`movie_id` and m2.`id`=md2.`movie_id` " + 
				" and (m1.`id`=m2.`id` or m1.`id`<>m2.`id`) " + 
				" and r1.`movie_id`=m1.`id` and r2.`movie_id`=m2.`id` " + 
				" and (r1.`movie_id`= r2.`movie_id`or r1.`movie_id`<> r2.`movie_id` ) " + 
				" and r1.`actor_id`=r2.`actor_id` " + 
				" GROUP BY id1,id2 ";
		List<Arco> result = new ArrayList<Arco>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setInt(2, anno);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Director d1 = new Director(res.getInt("id1"), res.getString("name1"), res.getString("last1"));
				Director d2 = new Director(res.getInt("id2"), res.getString("name2"), res.getString("last2"));
				Arco a= new Arco(d1,d2, res.getDouble("peso"));
				result.add(a);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	
	
}
