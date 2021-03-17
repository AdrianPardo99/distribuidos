  /*
   *  Funcion para borrar todos los usuarios de la BD.
   **/
  @POST
  @Path("borrar_usuarios")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.APPLICATION_JSON)
  public Response borrar_usuarios(@FormParam("borrar_usuarios") String email) throws Exception{
    Connection conexion= pool.getConnection();
    try{
      PreparedStatement stmt=conexion.prepareStatement("DELETE FROM fotos_usuarios");
      try{stmt.executeUpdate();}finally{stmt.close();}
      stmt=conexion.prepareStatement("DELETE FROM usuarios");
      try{stmt.executeUpdate();}finally{stmt.close();}
    }catch (Exception e){
      return Response.status(400).entity(j.toJson(new Error(e.getMessage()))).build();
    }finally{
      conexion.close();
    }
    return Response.ok().build();
  }
