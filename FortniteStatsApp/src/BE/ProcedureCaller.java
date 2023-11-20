package BE;
import java.sql.*;
public class ProcedureCaller {
    private String dbUrl = "jdbc:oracle:thin:@172.22.160.22:1521:XE";
    private String dbUser  = "C##FBPOOL211";
    private String dbPassword = "oracle";

    public Object[] callProcedure(String procedureName, Object[] inParams, int[] outParamTypes) {
        Object[] outParams = null;

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             CallableStatement stmt = createCallableStatement(conn, procedureName, inParams, outParamTypes)) {

            stmt.execute();

            outParams = new Object[outParamTypes.length];
            for (int i = 0; i < outParamTypes.length; i++) {
                outParams[i] = stmt.getObject(i + inParams.length + 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return outParams;
    }
    private CallableStatement createCallableStatement(Connection conn, String procedureName, Object[] inParams, int[] outParamTypes) throws SQLException {
        StringBuilder sb = new StringBuilder("{call ");
        sb.append(procedureName);
        sb.append("(");
        for (int i = 0; i < inParams.length + outParamTypes.length; i++) {
            sb.append("?");
            if (i < inParams.length + outParamTypes.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(")}");

        CallableStatement stmt = conn.prepareCall(sb.toString());

        for (int i = 0; i < inParams.length; i++) {
            stmt.setObject(i + 1, inParams[i]);
        }
        for (int i = 0; i < outParamTypes.length; i++) {
            stmt.registerOutParameter(inParams.length + i + 1, outParamTypes[i]);
        }

        return stmt;
    }
}
