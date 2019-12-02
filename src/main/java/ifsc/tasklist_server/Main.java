package ifsc.tasklist_server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.List;
import ifsc.tasklist.dbcontrol.Conn;
import ifsc.tasklist.dbcontrol.FeedbackDAO;
import ifsc.tasklist.dbcontrol.GoalsDAO;
import ifsc.tasklist.dbcontrol.ProjectDAO;
import ifsc.tasklist.dbcontrol.TarefaProjetoDAO;
import ifsc.tasklist.dbcontrol.TaskDAO;
import ifsc.tasklist.dbcontrol.UserDAO;
import ifsc.tasklist.dbentities.Feedback;
import ifsc.tasklist.dbentities.Goals;
import ifsc.tasklist.dbentities.Project;
import ifsc.tasklist.dbentities.TarefaProjeto;
import ifsc.tasklist.dbentities.Task;
import ifsc.tasklist.dbentities.User;
import ifsc.tasklist.exceptions.CommException;
import ifsc.tasklist.exceptions.NetDeviceException;
import ifsc.tasklist.exceptions.PortException;

public class Main {
	public static void main(String[] args) {

		ServerSocket server = null;
		try {
			Conn.getEntityManager().close();
			printServerInfo();
			server = openSocket();
			System.out.println("O servidor está aberto na porta " + server.getLocalPort());
			while (true) {
				listen(server);
			}
		} catch (PortException ex) {
			System.err.println("Nenhuma porta disponível no servidor.");
		} catch (NetDeviceException ex) {
			System.err.println("A placa de rede está com algum problema.");
		} catch (CommException ex) {
			System.err.println("Ocorreu algum problema em uma comunicação com um cliente.");
		} finally {
			if (server != null) {
				try {
					server.close();
				} catch (IOException e) {
				}
			}
			Conn.closeConn();
		}
	}

	private static void listen(ServerSocket server) throws CommException {
		try {
			// mÃƒÂ©todo para falar que o servidor deve aceitar conexÃƒÂµes
			Socket client = server.accept();
			process(client);
			client.close();
		} catch (IOException e) {
			throw new CommException();
		}
	}

	private static void process(Socket client) throws IOException {
		System.out.println("Cliente conectado: " + client.getInetAddress().getHostAddress());

		ObjectInputStream in = new ObjectInputStream(client.getInputStream());
		String msg = in.readUTF();
		System.out.println("Cliente enviou: " + msg);

		ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());

		String recebido[] = msg.split(";");
		switch(recebido[0]) {
		
		case "task":
			if (recebido[1].contentEquals("get"))
				getTask(out, recebido);
			else if (recebido[1].contentEquals("getAll"))
				getAllTask(out);
			else if (recebido[1].contentEquals("add"))
				addTask(out, recebido);
			else if (recebido[1].contentEquals("delete"))
				deleteTask(out, recebido);
			else if (recebido[1].contentEquals("update"))
				updateTask(out, recebido);
			
		out.flush();
		out.close();
		in.close();	
		break;
		
		case "project":
			if (recebido[1].contentEquals("get"))
				getProject(out, recebido);
			else if (recebido[1].contentEquals("getAll"))
				getAllProject(out);
			else if (recebido[1].contentEquals("add"))
				addProject(out, recebido);
			else if (recebido[1].contentEquals("delete"))
				deleteProject(out, recebido);
			else if (recebido[1].contentEquals("update"))
				updateProject(out, recebido);
			
		out.flush();
		out.close();
		in.close();	
		break;
		
		case "tarefaprojeto":
			if (recebido[1].contentEquals("get"))
				getTP(out, recebido);
			else if (recebido[1].contentEquals("getAll"))
				getAllTP(out);
			else if (recebido[1].contentEquals("add"))
				addTP(out, recebido);
			else if (recebido[1].contentEquals("delete"))
				deleteTP(out, recebido);
			else if (recebido[1].contentEquals("update"))
				updateTP(out, recebido);
			
		out.flush();
		out.close();
		in.close();	
		break;
		
		case "user":
			if (recebido[1].contentEquals("get"))
				getUser(out, recebido);
			else if (recebido[1].contentEquals("getAll"))
				getAllUser(out);
			else if (recebido[1].contentEquals("add"))
				addUser(out, recebido);
			else if (recebido[1].contentEquals("delete"))
				deleteUser(out, recebido);
			else if (recebido[1].contentEquals("update"))
				updateUser(out, recebido);
		
		out.flush();
		out.close();
		in.close();
		break;
		
		case "goals":
			if (recebido[1].contentEquals("get"))
				getGoals(out, recebido);
			else if (recebido[1].contentEquals("getAll"))
				getAllGoals(out);
			else if (recebido[1].contentEquals("add"))
				addGoals(out, recebido);
			else if (recebido[1].contentEquals("delete"))
				deleteGoals(out, recebido);
			else if (recebido[1].contentEquals("update"))
				updateGoals(out, recebido);
		
		out.flush();
		out.close();
		in.close();
		break;
			
		case "feedback":
			if (recebido[1].contentEquals("get"))
				getFeedback(out, recebido);
			else if (recebido[1].contentEquals("getAll"))
				getAllFeedback(out);
			else if (recebido[1].contentEquals("add"))
				addFeedback(out, recebido);
			else if (recebido[1].contentEquals("delete"))
				deleteFeedback(out, recebido);
			else if (recebido[1].contentEquals("update"))
				updateFeedback(out, recebido);
		
		out.flush();
		out.close();
		in.close();
		break;
		}
	}


	private static void updateTask(ObjectOutputStream out, String[] recebido) throws IOException {
		Task task = new Task(recebido[2], recebido[3], recebido[4]);
		new TaskDAO().update(task);
	}

	private static void deleteTask(ObjectOutputStream out, String[] recebido) throws IOException {
		Task task = new Task(recebido[2],  recebido[3], recebido[4]);
		new TaskDAO().delete(task);
	}

	private static void addTask(ObjectOutputStream out, String[] recebido) throws IOException {
		Task task = new Task(recebido[2],  recebido[3], recebido[4]);
		new TaskDAO().add(task);
	}

	private static void getAllTask(ObjectOutputStream out) throws IOException {
		String msg = "";
		List<Task> tasks = new TaskDAO().getAll();
		if (tasks == null)
			out.writeUTF("404");
		else
			for (Task task : tasks)
				msg = msg.concat(task.getTitulo() + ";" + task.getDescricao() + ";" + task.getData() + ";");
		out.writeUTF(msg);
	}

	private static void getTask(ObjectOutputStream out, String[] recebido) throws IOException {
		Task task = new TaskDAO().get(recebido[2]);
		if (task == null) {
			out.writeUTF("404");
		} else {
			out.writeUTF(task.getTitulo() + ";" + task.getDescricao() + ";" + task.getData() + ";");
		}
	}

	
	/* ----------------------------------------------------------------------------------- */
	
	
	
	private static void updateUser(ObjectOutputStream out, String[] recebido) throws IOException {
		User user = new User(recebido[2], recebido[3], recebido[4], recebido[5]);
		new UserDAO().update(user);
	}

	private static void deleteUser(ObjectOutputStream out, String[] recebido) throws IOException {
		User user = new User(recebido[2], recebido[3], recebido[4], recebido[5]);
		new UserDAO().delete(user);
	}

	private static void addUser(ObjectOutputStream out, String[] recebido) throws IOException {
		User user = new User(recebido[2], recebido[3], recebido[4], recebido[5]);
		new UserDAO().add(user);
	}

	private static void getAllUser(ObjectOutputStream out) throws IOException {
		String msg = "";
		List<User> users = new UserDAO().getAll();
		if (users == null)
			out.writeUTF("404");
		else
			for (User user : users)
				msg = msg.concat(user.getUsuario() + ";" + user.getEmail() + ";" + user.getSenha() + ";" + user.getImage() + ";");
		out.writeUTF(msg);
	}

	private static void getUser(ObjectOutputStream out, String[] recebido) throws IOException {
		User user = new UserDAO().get(recebido[2]);
		if (user == null) {
			out.writeUTF("404");
		} else {
			out.writeUTF(user.getUsuario() + ";" + user.getEmail() + ";" + user.getSenha() + ";" + user.getImage() + ";");
		}
	}
	
	
	
	/* ----------------------------------------------------------------------------------- */
	
	
	
	private static void updateProject(ObjectOutputStream out, String[] recebido) throws IOException {
		Project project = new Project(recebido[2], recebido[3]);
		new ProjectDAO().update(project);
	}

	private static void deleteProject(ObjectOutputStream out, String[] recebido) throws IOException {
		Project project = new Project(recebido[2], recebido[3]);
		new ProjectDAO().delete(project);
	}

	private static void addProject(ObjectOutputStream out, String[] recebido) throws IOException {
		Project project = new Project(recebido[2], recebido[3]);
		new ProjectDAO().add(project);
	}

	private static void getAllProject(ObjectOutputStream out) throws IOException {
		String msg = "";
		List<Project> projects = new ProjectDAO().getAll();
		if (projects == null)
			out.writeUTF("404");
		else
			for (Project project: projects)
				msg = msg.concat(project.getTitulo() + ";" + project.getObjetivo() + ";");
		out.writeUTF(msg);
	}

	private static void getProject(ObjectOutputStream out, String[] recebido) throws IOException {
		Project project = new ProjectDAO().get(recebido[2]);
		if (project == null) {
			out.writeUTF("404");
		} else {
			out.writeUTF(project.getTitulo() + ";" + project.getObjetivo() + ";");
		}
	}
	
	/* ----------------------------------------------------------------------------------- */
	
	
	
	private static void updateTP(ObjectOutputStream out, String[] recebido) throws IOException {
		TarefaProjeto tp = new TarefaProjeto(recebido[2],  recebido[3], recebido[4], recebido[5]);
		new TarefaProjetoDAO().update(tp);
	}

	private static void deleteTP(ObjectOutputStream out, String[] recebido) throws IOException {
		TarefaProjeto tp = new TarefaProjeto(recebido[2],  recebido[3], recebido[4], recebido[5]);
		new TarefaProjetoDAO().delete(tp);
	}

	private static void addTP(ObjectOutputStream out, String[] recebido) throws IOException {
		TarefaProjeto tp = new TarefaProjeto(recebido[2],  recebido[3], recebido[4], recebido[5]);
		new TarefaProjetoDAO().add(tp);
	}

	private static void getAllTP(ObjectOutputStream out) throws IOException {
		String msg = "";
		List<TarefaProjeto> tp = new TarefaProjetoDAO().getAll();
		if (tp == null)
			out.writeUTF("404");
		else
			for (TarefaProjeto tpj: tp)
				msg = msg.concat(tpj.getTitulo() + ";" + tpj.getDescricao() + ";" + tpj.getProjeto() + ";" + tpj.getData() + ";");
		out.writeUTF(msg);
	}

	private static void getTP(ObjectOutputStream out, String[] recebido) throws IOException {
		TarefaProjeto tarefaproj = new TarefaProjetoDAO().get(recebido[2]);
		if (tarefaproj == null) {
			out.writeUTF("404");
		} else {
			out.writeUTF(tarefaproj.getTitulo() + ";" + tarefaproj.getDescricao() + ";" + tarefaproj.getProjeto() + ";" + tarefaproj.getData() + ";");
		}
	}
	
	/* ----------------------------------------------------------------------------------- */
	
	private static void updateGoals(ObjectOutputStream out, String[] recebido) throws IOException {
		Goals goals = new Goals(recebido[2], recebido[3], recebido[4], recebido[5],   Integer.valueOf(recebido[6]));
		new GoalsDAO().update(goals);
	}

	private static void deleteGoals(ObjectOutputStream out, String[] recebido) throws IOException {
		Goals goals = new Goals(recebido[2], recebido[3], recebido[4], recebido[5],   Integer.valueOf(recebido[6]));
		new GoalsDAO().delete(goals);
	}

	private static void addGoals(ObjectOutputStream out, String[] recebido) throws IOException {
		Goals goals = new Goals(recebido[2], recebido[3], recebido[4], recebido[5],  Integer.valueOf(recebido[6]));
		new GoalsDAO().add(goals);
	}

	private static void getAllGoals(ObjectOutputStream out) throws IOException {
		String msg = "";
		List<Goals> goals = new GoalsDAO().getAll();
		if (goals == null)
			out.writeUTF("404");
		else
			for (Goals goal: goals)
				msg = msg.concat(goal.getNomeUser()+ ";" + goal.getMetaTarefaCumprida() + ";" + goal.getMetaProjetoCumprida() + ";" + goal.getMetaTPCumprida() + ";" + goal.getObjDiario() + ";");
		out.writeUTF(msg);
	}

	private static void getGoals(ObjectOutputStream out, String[] recebido) throws IOException {
		Goals goal = new GoalsDAO().get(recebido[2]);
		if (goal == null) {
			out.writeUTF("404");
		} else {
			out.writeUTF(goal.getNomeUser()+ ";" + goal.getMetaTarefaCumprida() + ";" + goal.getMetaProjetoCumprida() + ";" + goal.getMetaTPCumprida() + ";" + goal.getObjDiario() + ";");
		}
	}	
	
	
/* ----------------------------------------------------------------------------------- */
	
	
	
	private static void updateFeedback(ObjectOutputStream out, String[] recebido) throws IOException {
		Feedback feedback = new Feedback(recebido[2], Integer.valueOf(recebido[3]), Boolean.valueOf(recebido[4]), recebido[5]);
		new FeedbackDAO().update(feedback);
	}

	private static void deleteFeedback(ObjectOutputStream out, String[] recebido) throws IOException {
		Feedback feedback = new Feedback(recebido[2], Integer.valueOf(recebido[3]), Boolean.valueOf(recebido[4]), recebido[5]);
		new FeedbackDAO().delete(feedback);
	}

	private static void addFeedback(ObjectOutputStream out, String[] recebido) throws IOException {
		Feedback feedback = new Feedback(recebido[2], Integer.valueOf(recebido[3]), Boolean.valueOf(recebido[4]), recebido[5]);
		new FeedbackDAO().add(feedback);
	}

	private static void getAllFeedback(ObjectOutputStream out) throws IOException {
		String msg = "";
		List<Feedback> fbs = new FeedbackDAO().getAll();
		if (fbs == null)
			out.writeUTF("404");
		else
			for (Feedback fb : fbs)
				msg = msg.concat(fb.getUser() + ";" + fb.getNota() + ";" + fb.isGostou() + ";" + fb.getComentario() + ";");
		out.writeUTF(msg);
	}

	private static void getFeedback(ObjectOutputStream out, String[] recebido) throws IOException {
		Feedback fb = new FeedbackDAO().get(recebido[2]);
		if (fb == null) {
			out.writeUTF("404");
		} else {
			out.writeUTF(fb.getUser() + ";" + fb.getNota() + ";" + fb.isGostou() + ";" + fb.getComentario() + ";");
		}
	}
	
	/* ----------------------------------------------------------------------------------- */
	
	
	private static ServerSocket openSocket() throws PortException {
		int port = 1024;
		while (port <= 65535) {
			try {
				return new ServerSocket(port);
			} catch (IOException ex) {
				port++;
			}
		}
		throw new PortException();
	}

	private static void printServerInfo() throws NetDeviceException {
		try {
			System.out.println("-----------------------------------");
			System.out.println("InformaÃƒÂ§ÃƒÂµes do servidor:");
			String hostname = InetAddress.getLocalHost().getHostName();
			System.out.println("Hostname: " + hostname);
			Enumeration<NetworkInterface> myNetInterfaces = NetworkInterface.getNetworkInterfaces();
			while (myNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = myNetInterfaces.nextElement();
				System.out.println("Interface: " + netInterface.getName());
				Enumeration<InetAddress> interfaceAddrs = netInterface.getInetAddresses();
				while (interfaceAddrs.hasMoreElements()) {
					InetAddress addr = interfaceAddrs.nextElement();
					System.out.println("  " + addr.getHostAddress());
				}
			}
			System.out.println("-----------------------------------");
		} catch (SocketException e1) {
			throw new NetDeviceException();
		} catch (UnknownHostException e) {
			throw new NetDeviceException();
		}
	}
}
