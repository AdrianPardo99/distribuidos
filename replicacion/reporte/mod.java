public static void main(String[] args) throws Exception{
  /* Aniadir estas lineas */
  if(args.length<1){
    System.out.println("Error\n\tUsage: java Cliente2 <IP>");
    System.exit(1);
  }
  Socket conexion = null;
  /* Aniadir esto */
  System.out.println("Conectando a: "+args[0]);
  for(;;)
    try{
      conexion = new Socket(args[0],50000); /* Modificacion aqui */
        break;
    }catch (Exception e){
      Thread.sleep(100);
    }
  /* Resto de codigo */
  System.out.println("Saliendo...");
}
