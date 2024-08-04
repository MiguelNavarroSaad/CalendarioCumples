import java.util.*;
import java.util.regex.*;  
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.text.html.*;
import javax.imageio.*;
import java.io.*;
import java.nio.file.*;
import java.lang.*;

public class CalendarioCumples extends JFrame {

    static private final String linea_nueva = "\n";
    String diagonal = System.getProperty("file.separator");
    JPanel receptor;
    String diaElegido;
    JLabel nombreMA;
    JLabel nombreDSD;
    JLabel cumpleHoy;
    JLabel nueva;
    JLabel creciente;
    JLabel llena;
    JLabel menguante;
    JDialog dialogo;
    DefaultListModel modelo = new DefaultListModel<>();
    String fotosDir[];
    JTextField quienCumple;
    JTextField queMes;
    JTextField queDia;
    String miFolder;
    String rutaf = System.getProperty("user.dir") + diagonal + "fotos";
    String rutan = System.getProperty("user.dir") + diagonal + "numenes";
    String rutac;
    Integer numeralAnio = 0;
    Integer numeralDia = 0;
    JPanel numeroAzteca;
    ImageIcon numenAA;
    ImageIcon numenDA;
    JButton anioAzteca;
    JButton diaAzteca;
    JButton boton_veintena;
    
    Integer anchoCuadroMes = 400;
    Integer altoCuadroMes = 380;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double ancho = (int) screenSize.getWidth();
    double alto = (int) screenSize.getHeight();
    Integer equis = (int) ancho - anchoCuadroMes;

    GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
    Integer anio = cal.get(Calendar.YEAR);
    Integer mes = cal.get(Calendar.MONTH);
    Integer hoy = cal.get(Calendar.DATE);
    final Integer anio_inicio = anio;
    final Integer mes_inicio = mes;
    final Integer dia_inicio = hoy;
    Integer numdias[] = {31, ((cal.isLeapYear(anio)) ? 29 : 28), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    String nomMes[] = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio",
                       "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
    String nomDia[] = {"", "Domingo", "Lunes", "Martes", "Mi\u00e9rcoles", "Jueves", "Viernes", "S\u00e1bado"};
    
    luna miluna = new luna();
    Integer datosLunares[] = {0,0,0,0,0};

    String nomGlifoE[]={"Caim\u00e1n","Viento","Casa","Lagartija","Serpiente","Muerte","Venado","Conejo",
            "Agua","Perro","Mona","Hierba","Carrizo","Ocelote","\u00c1guila",
            "\u00c1guila real","Movimiento","Pedernal","Lluvia","Flor"};
    String nomNum[]={"","Ce","Ome","Yei","Nahui","Macuili","Chicuace","Chicome","Chicuei","Chiconahui",
            "Matlactli","Matlactlionce","Matlactliomome","Matlactliomei"};
    String nomGlifo[]={"Cipactli","Eh\u00e9catl","Calli","Cuezpallin","C\u00f3atl","Miquistli","M\u00e1zatl","Tochtli",
            "Atl","Izcuintli","Osomatli","Malinalli","\u00c1catl","Oc\u00e9lotl","Cuauhtli",
            "Coscacuauhtli","Oll\u00edn","T\u00e9cpatl","Qui\u00e1huitl","Xochitl"};
    String mesAzteca[]={"Atlacaualo","Tlacaxipehualiztli","Tozoztontli","Hueytozoztli","T\u00f3xcatl",
            "Itzacualiztli","Tecuilhuitontli","Hueytecu\u00edlhuitl","Tlaxochimaco","Xocothuezi",
            "Ochpaniztli","Teotleco","Tepe\u00edhuitl","Quecholli","Panquezaliztli","Atemoztli","T\u00edtitl",
            "Izcalli","Nemontemi"};
    String significadoMesAzteca[] ={"Lo que dejan las aguas (Tl\u00e1loc)",
            "Desollamiento de gentes (Xipe t\u00f3tec)",
            "Velaci\u00f3n (Tl\u00e1loc)",
            "Gran velaci\u00f3n (Tl\u00e1loc)",
            "Sequ\u00eda (Tezcatlipoca)",
            "Preparaci\u00f3n del itzalli \u2014frijol y ma\u00edz (Tl\u00e1loc, Quetzalc\u00f3atl y X\u00f3lotl)",
            "Fiesta de los señores (Quetzalc\u00f3atl, Cihuac\u00f3atl, Cinte\u00f3tl y Ixtl\u00edltzin)",
            "Gran fiesta de los señores ( Cinte\u00f3tl y Xipe t\u00f3tec)",
            "Fiesta de las flores (Cihuac\u00f3atl)",
            "Maduraci\u00f3n de los frutos (Xiuteuctli)",
            "\u00c9poca de la limpieza \u2014cosecha (Toci, Xicomec\u00f3atl y Atlat\u00f3nan)",
            "Llegada de los dioses (Xochiquetzalli, Tezcatlipoca y Huitzilopochtli)",
            "Fiesta de los cerros (Xochiquetzalli, Tl\u00e1loc y Napatecutli)",
            "Fiesta de las flechas (Mixc\u00f3atl y Tezcatlipoca tlamatcincatl)",
            "Despliegue de banderas (Huitzilopochtli)",
            "Descenso de las aguas (Chalchiuitl)",
            "Resurgimiento de la naturaleza (Cihuac\u00f3atl)",
            "Surgimiento de la naturaleza (Xiuteuctli)",
            "D\u00edas festivos (sin numen conocido)"};
    String iconoNumen[] = {"cipactli", "ehecatl", "cali", "cuespali",
            "coatl", "mikistli", "mazatl", "tochtli",
            "atl", "scuintli", "osomatli", "malinali",
            "acatl", "ocelotl", "cuautli", "coscacua",
            "olin", "tecpatl", "kiauitl", "xochitl"};

    public CalendarioCumples() {
        this.getContentPane().setLayout(new GridBagLayout());
        
        botonFechaActual();
        panelSuperior();
        panelCumple();
        panelMes(anio, mes, hoy);
        panelDia(anio, mes, hoy);
        panelAutor();
        seleccionaDia(mes, hoy);
        cierre();
        eligeCumple();
        panelLunas();
        fasesLuna(anio, mes);
        laFechaAzteca(anio, mes, hoy);
        fechaAzteca();
        
        setTitle("Calendario de cumplea\u00f1os");
        setResizable(false);
        setUndecorated(true);
        setLocation(equis, 0);
        setSize(anchoCuadroMes, altoCuadroMes);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void panelSuperior() {
        FlowLayout contenidoSup = new FlowLayout(FlowLayout.CENTER, 3,3);
        JPanel panelSup = new JPanel ();
        panelSup.setLayout(contenidoSup);
        add(panelSup);

        nombreMA = new JLabel(nomMes[mes] + " de " + anio, JLabel.CENTER);
        nombreMA.setPreferredSize(new Dimension(140, 25));
        nombreMA.setForeground(new Color(0x00CDD4));
        
        JButton anterior = new JButton("\u23f4"); // \u25c0
        anterior.setFont(anterior.getFont().deriveFont(20f));
        anterior.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (mes == 0) {
                    anio -= 1;
                    mes = 11;
                }
                else
                    mes -= 1;
                nombreMA.setText(nomMes[mes] + " de " + anio);
                remove(receptor);
                panelMes(anio, mes, 1);
                nombreDSD.setText("            ");
                panelDia(anio, mes, 1);
                seleccionaDia(mes, 1);
                nueva.setText("                        ");
                creciente.setText("                        ");
                llena.setText("                        ");
                menguante.setText("                        ");
                panelLunas();
                fasesLuna(anio, mes);
                laFechaAzteca(anio, mes, 1);
           }
        });
        anterior.setMargin(new Insets(0,0,0,0));
        anterior.setBackground(new Color(238,238,238));
        anterior.setBorderPainted(false);

        JButton siguiente = new JButton("\u23f5"); // \u25b6
        siguiente.setFont(siguiente.getFont().deriveFont(20f));
        siguiente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (mes == 11) {
                    anio += 1;
                    mes = 0;
                }
                else
                    mes += 1;
                nombreMA.setText(nomMes[mes] + " de " + anio);
                remove(receptor);
                panelMes(anio, mes, 1);
                nombreDSD.setText("            ");
                panelDia(anio, mes, 1);
                seleccionaDia(mes, 1);
                nueva.setText("                        ");
                creciente.setText("                        ");
                llena.setText("                        ");
                menguante.setText("                        ");
                panelLunas();
                fasesLuna(anio, mes);
                laFechaAzteca(anio, mes, 1);
            }
        });
        siguiente.setMargin(new Insets(0,0,0,0));
        siguiente.setBackground(new Color(238,238,238));
        siguiente.setBorderPainted(false);
        
        panelSup.add(anterior);
        panelSup.add(nombreMA);
        panelSup.add(siguiente);
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 7;
        constraints.gridheight = 2;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        this.getContentPane().add (panelSup, constraints);
    }
    
    private void panelCumple() {
        cumpleHoy = new JLabel();
        cumpleHoy.setForeground(Color.RED);
        cumpleHoy.setOpaque(true);
        cumpleHoy.setBackground(new Color(0xEEEEEE));
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 6;
        constraints.gridheight = 2;
        constraints.insets = new Insets(0,10,0,0);
        this.getContentPane().add (cumpleHoy, constraints);
        this.getContentPane().setComponentZOrder(cumpleHoy, 0);
    }
    
    private void panelMes(int anio, int mes, int hoy) {
        GridLayout matrizDias = new GridLayout(0,7,5,5);
        receptor = new JPanel();
        receptor.setLayout(matrizDias);
        add(receptor);
        
        JLabel nomdias[] = new JLabel[7];
        String nomdia[] = {"Do", "Lu", "Ma", "Mi", "Ju", "Vi", "Sa"};

        for (int i = 0; i < nomdias.length; i++) {
            nomdias[i] = new JLabel(nomdia[i]);
            if (i == 0)
                nomdias[i].setForeground(Color.RED);
            receptor.add(nomdias[i]);
        }
        
        Integer dia = 1;
        cal.set(anio, mes, dia);
        Integer diaSem = cal.get(Calendar.DAY_OF_WEEK);
        Integer diasMes = numdias[mes];

        Integer blancos = diaSem - 1;
        JLabel espacio[] = new JLabel[blancos];
        for (int i = 0; i < blancos; i++) {
            espacio[i] = new JLabel("  ");
            receptor.add(espacio[i]);
        }

        String numero = "";
        final Integer anio_elegido = anio;
        final Integer mes_elegido = mes;
        JButton llave[] = new JButton[diasMes + 1];
        for (int i = 1; i <= diasMes; i++) {
            numero = ((i < 10) ? " " : "") + Integer.toString(i);
            llave[i] = new JButton(numero);
            llave[i].setActionCommand(numero);
            llave[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    JButton boton = (JButton) event.getSource();
                    String dia_elegido = boton.getActionCommand();
                    nombreDSD.setText("            ");
                    diaElegido = dia_elegido.trim();
                    panelDia(anio_elegido, mes_elegido, Integer.parseInt(dia_elegido.trim()));
                    seleccionaDia(mes_elegido, Integer.parseInt(dia_elegido.trim()));
                    laFechaAzteca(anio_elegido, mes_elegido, Integer.parseInt(dia_elegido.trim()));
                }
            });
            if (diaSem % 7 == 1)
                llave[i].setForeground(Color.RED);
            if (i == hoy)
                llave[i].setForeground(Color.BLUE);
            llave[i].setMargin(new Insets(0,0,0,0));
            llave[i].setBackground(new Color(238,238,238));
            llave[i].setBorderPainted(false);
            receptor.add(llave[i]);
            diaSem++;
        }
        
        JLabel restantes[] = new JLabel[7];
        for (int i = 1; i < 7; i++) {
            restantes[i] = new JLabel("  ");
            receptor.add(restantes[i]);
        }
    
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 7;
        constraints.gridheight = 10;
        constraints.insets = new Insets(5,5,5,5);
        this.getContentPane().add(receptor, constraints);
    }
    
    private void panelDia(int anio, int mes, int dia) {
        cal.set(anio, mes, dia);
        Integer diaSem = cal.get(Calendar.DAY_OF_WEEK);
        nombreDSD = new JLabel(nomDia[diaSem] + "  " + dia, JLabel.RIGHT);
        nombreDSD.setForeground(new Color(0x00CDD4));
        nombreDSD.setFont(nombreDSD.getFont().deriveFont(18f));
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 15;
        constraints.gridwidth = 6;
        constraints.gridheight = 2;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        this.getContentPane().add(nombreDSD, constraints);
    }
    
    private void seleccionaDia(int mes, int dia) {
        String nombre = "";
        miFolder = System.getProperty("user.dir");
        String foto = miFolder + diagonal + "bca.png";
        ponFoto(foto);
        miFolder = miFolder + diagonal + "fotos" + diagonal + nomMes[mes].toLowerCase();
        cumpleHoy.setText("                                          ");
        panelCumple();
        File carpeta = new File(miFolder);
        if (carpeta.exists() && carpeta.isDirectory()) {
            String fotos_cumple[] = carpeta.list();
            if (fotos_cumple.length != 0 && fotos_cumple != null) {
                boolean selector = false;
                File archivo;
                Integer indice;
                for (int i = 0; i < fotos_cumple.length && selector == false; i++) {
                    selector = fotos_cumple[i].contains(((dia < 10) ? "0" : "") + Integer.toString(dia));
                    if (selector) {
                        archivo = new File(fotos_cumple[i]);
                        nombre = archivo.getName();
                        indice = nombre.lastIndexOf(".") - 2;
                        nombre = nombre.substring(0,indice);
                        nombre = "Cumple " + String.valueOf(nombre).replaceAll("[A-Z]", " $0").trim();
                        cumpleHoy.setText(nombre);
                        foto = miFolder + diagonal + fotos_cumple[i];
                    }
                }
            }
        }
        else
            carpeta.mkdirs();
        ponFoto(foto);
    }
    
    private void ponFoto(String foto) {
        ImageIcon laFoto = new ImageIcon(foto);
        Integer anchura = laFoto.getIconWidth();
        Integer altura = laFoto.getIconHeight();
        altura = (int) (altura * 120) / anchura;
        anchura = 120;
        if (altura > 120) {
            anchura = (int) (anchura * 120) / altura;
            altura = 120;
        }
        laFoto = new ImageIcon(laFoto.getImage().getScaledInstance(anchura, altura, java.awt.Image.SCALE_SMOOTH));
        JLabel espacioFoto = new JLabel(laFoto);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 9;
        constraints.gridy = 0;
        constraints.gridwidth = 25;
        constraints.gridheight = 15;
        constraints.insets = new Insets(0,0,0,0);
        this.getContentPane().add(espacioFoto, constraints);
        this.getContentPane().setComponentZOrder(espacioFoto, 0);
    }
    
    private void panelLunas() {
        JPanel cuadroLunas = new JPanel ();
        cuadroLunas.setLayout(new GridLayout(4,1));
        add(cuadroLunas);

        for (int i = 0; i < 4; i++) datosLunares[i] = 0;
        nueva = new JLabel("");
        nueva.setFont(nueva.getFont().deriveFont(10f));
        nueva.setOpaque(true);
        nueva.setForeground(Color.RED);
        nueva.setBackground(new Color(0xEEEEEE));
        creciente = new JLabel("");
        creciente.setFont(creciente.getFont().deriveFont(10f));
        creciente.setOpaque(true);
        creciente.setForeground(Color.RED);
        creciente.setBackground(new Color(0xEEEEEE));
        llena = new JLabel("");
        llena.setFont(llena.getFont().deriveFont(10f));
        llena.setOpaque(true);
        llena.setForeground(Color.RED);
        llena.setBackground(new Color(0xEEEEEE));
        menguante = new JLabel("");
        menguante.setFont(menguante.getFont().deriveFont(10f));
        menguante.setOpaque(true);
        menguante.setForeground(Color.RED);
        menguante.setBackground(new Color(0xEEEEEE));
        
        cuadroLunas.add(llena);
        cuadroLunas.add(menguante);
        cuadroLunas.add(nueva);
        cuadroLunas.add(creciente);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 7;
        constraints.gridy = 16;
        constraints.gridwidth = 0;
        constraints.gridheight = 1;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        this.getContentPane().add(cuadroLunas, constraints);
        this.getContentPane().setComponentZOrder(cuadroLunas, 0);
    }

    private void fasesLuna(int anio, int mes) {
        Integer dia = 1;
        cal.set(anio, mes, dia);
        Integer diasMes = numdias[mes];

        double husohorario = -6.0 / 24.0; // CIUDAD DE MEXICO
        Integer n = (int) (Math.floor(12.37*(anio-1900+((1.0*(mes+1)-0.5)/12.0))));
        Integer nph = 0;
        for (int diacorriente = 1; diacorriente <= diasMes; diacorriente++) {
            double diajuliano = miluna.julday(diacorriente,(mes+1),anio);
            miluna.flmoon(n,nph);
            n += (int) (Math.floor(1.0*(diajuliano-miluna.jd)/28.0));
            for (int i = 1; i <= 4; i++) {
                miluna.flmoon(n, nph);
                miluna.frac = 24.0 * (miluna.frac + husohorario);
                if (miluna.frac < 0) {
                    --miluna.jd;
                    miluna.frac += 24.0;
                }
                if (miluna.frac > 12.0) {
                    ++miluna.jd;
                    miluna.frac -= 12.0;
                } else
                    miluna.frac += 12.0;
                double hrs = Math.floor(miluna.frac);
                miluna.frac = 60.0 * (miluna.frac - hrs);
                double mins = Math.floor(miluna.frac);
                double segs = Math.floor(60.0 * (miluna.frac - mins));
                miluna.caldat(miluna.jd);
                if (diajuliano == miluna.jd) {
                    JLabel dia_fase = new JLabel(Integer.toString(miluna.day));
                    if (datosLunares[nph] == 0) {
                        switch (nph) {
                            case 0:
                                nueva.setText(dia_fase.getText() + " " + Character.toString(0x1F311) + Integer.toString((int) hrs) + ":"
                                    + Integer.toString((int) mins) + ":" + Integer.toString((int) segs));
                                break;
                            case 1:
                                creciente.setText(dia_fase.getText() + " " + Character.toString(0x1F313) + Integer.toString((int) hrs) + ":"
                                    + Integer.toString((int) mins) + ":" + Integer.toString((int) segs));
                                break;
                            case 2:
                                llena.setText(dia_fase.getText() + " " + Character.toString(0x1F315) + Integer.toString((int) hrs) + ":"
                                    + Integer.toString((int) mins) + ":" + Integer.toString((int) segs));
                                break;
                            case 3:
                                menguante.setText(dia_fase.getText() + " " + Character.toString(0x1F317) + Integer.toString((int) hrs) + ":"
                                    + Integer.toString((int) mins) + ":" + Integer.toString((int) segs));
                                break;
                           default:
                                break;
                        }
                        datosLunares[nph] = miluna.day;
                    }
                    else {
                        datosLunares[4] = miluna.day;
                        switch (nph) {
                            case 0:
 /*                               nueva.setText(nueva.getText().substring(0,nueva.getText().lastIndexOf(Character.toString(0x1F311)))
                                + ", "  + dia_fase.getText() + " "
                                + nueva.getText().substring(nueva.getText().lastIndexOf(Character.toString(0x1F311)+1)) + ", "
                                + Integer.toString((int) hrs) + ":" + Integer.toString((int) mins) + ":" + Integer.toString((int) segs));
*/
                                break;
                            case 1:
/*                                creciente.setText(creciente.getText().substring(0,creciente.getText().lastIndexOf(Character.toString(0x1F313)))
                                + ", " + dia_fase.getText() + " "
                                + creciente.getText().substring(creciente.getText().lastIndexOf(Character.toString(0x1F313)+1)) + ", "
                                + Integer.toString((int) hrs) + ":" + Integer.toString((int) mins) + ":" + Integer.toString((int) segs));
*/
                                break;
                            case 2:
/*                                llena.setText(llena.getText().substring(0,llena.getText().lastIndexOf(Character.toString(0x1F315)))
                                + ", " + dia_fase.getText() + " "
                                + llena.getText().substring(llena.getText().lastIndexOf(Character.toString(0x1F315)+1)) + ", "
                                + Integer.toString((int) hrs) + ":" + Integer.toString((int) mins) + ":" + Integer.toString((int) segs));
*/
                                break;
                            case 3:
/*                                menguante.setText(menguante.getText().substring(0,menguante.getText().lastIndexOf(Character.toString(0x1F317)))
                                + ", " + dia_fase.getText() + " "
                                + menguante.getText().substring(menguante.getText().lastIndexOf(Character.toString(0x1F317)+1)) + ", "
                                + Integer.toString((int) hrs) + ":" + Integer.toString((int) mins) + ":" + Integer.toString((int) segs));
*/
                                break;
                           default:
                                break;
                        }
                    }
                }
                nph = (nph + 1) % 4;
            }
        }
    }

    private void cierre() {
        JButton boton_salir = new JButton("Cerrar");
        boton_salir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                dispose();
            }
        });
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 16;
        constraints.gridwidth = 1;
        constraints.gridheight = 2;
        constraints.insets = new Insets(0,10,10,0);
        this.getContentPane().add(boton_salir, constraints);
    }
   
    private void eligeCumple() {
        JButton boton_cumple = new JButton("Elige cumple");
        boton_cumple.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                nuevoDialogo();
            }
        });

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 25;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.insets = new Insets(10,0,0,0);
        this.getContentPane().add(boton_cumple, constraints);
    }
    
    private void fechaAzteca() {
        JButton boton_fecha = new JButton("Fecha azteca");
        boton_fecha.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                panelAzteca();
            }
        });

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 25;
        constraints.gridy = 15;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.insets = new Insets(10,10,10,0);
        this.getContentPane().add(boton_fecha, constraints);
    }
    
    private void panelAutor() {
        JButton nombreAutor = new JButton("\u00a9");  //  \u00ae Derechos Reservados 
        nombreAutor.setFont(nombreAutor.getFont().deriveFont(20f));
        nombreAutor.setMargin(new Insets(0,0,0,0));
        nombreAutor.setBackground(new Color(238,238,238));
        nombreAutor.setBorderPainted(false);
        nombreAutor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, "2021, Calendario para cumplea\u00f1os, V1.3.\n"
                    + "Miguel Navarro Saad.\n"
                    + "Ciudad de M\u00e9xico, M\u00c9XICO.\n mnavsa@yahoo.com\n\n\n", "Autor", JOptionPane.PLAIN_MESSAGE);
            }
        });

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 27;
        constraints.gridy = 17;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        this.getContentPane().add(nombreAutor, constraints);
    }
    
    private void botonFechaActual() {
        JButton retorno = new JButton("\u21bb");
        retorno.setFont(retorno.getFont().deriveFont(20f));
        retorno.setMargin(new Insets(0,0,0,0));
        retorno.setBackground(new Color(238,238,238));
        retorno.setBorderPainted(false);
        retorno.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                fechaActual(anio_inicio, mes_inicio, dia_inicio);
            }
        });

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 3;
        constraints.gridy = 16;
        constraints.gridwidth = 1;
        constraints.gridheight = 2;
        constraints.insets = new Insets(0,10,10,0);
        this.getContentPane().add(retorno, constraints);
    }

    private void fechaActual(int anio_actual, int mes_actual, int dia_actual) {
        nombreMA.setText(nomMes[mes_actual] + " de " + anio_actual);
        nombreDSD.setText("            ");
        remove(receptor);
        panelMes(anio_actual, mes_actual, dia_actual);
        panelDia(anio_actual, mes_actual, dia_actual);
        seleccionaDia(mes_actual, dia_actual);
        nueva.setText("                        ");
        creciente.setText("                        ");
        llena.setText("                        ");
        menguante.setText("                        ");
        panelLunas();
        fasesLuna(anio_actual, mes_actual);
        laFechaAzteca(anio_actual, mes_actual, dia_actual);
        anio = anio_actual;
        mes = mes_actual;
        hoy = dia_actual;
    }

    private void panelAzteca() {
        JDialog dialogo = new JDialog(CalendarioCumples.this);
        dialogo.setLayout(new GridBagLayout());
        setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);

        Integer anchoCuadroDialogo = 250;
        Integer altoCuadroDialogo = 275;
        Integer x = (int) (ancho - anchoCuadroDialogo - anchoCuadroMes);
        Integer y = altoCuadroMes / 2;

        JPanel cuadroFecha = new JPanel();
        cuadroFecha.setLayout(new GridBagLayout());
        cuadroFecha.setBorder(BorderFactory.createEmptyBorder(-75,0,0,0));
        
        puntos(numeralAnio);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        cuadroFecha.add(numeroAzteca, constraints);

        numeroAzteca = new JPanel();
        puntos(numeralDia);
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        cuadroFecha.add(numeroAzteca, constraints);

        Integer anchura = numenAA.getIconWidth();
        Integer altura = numenAA.getIconHeight();
        altura = (int) (altura * 80) / anchura;
        anchura = 80;
        if (altura > 80) {
            anchura = (int) (anchura * 80) / altura;
            altura = 80;
        }
        numenAA = new ImageIcon(numenAA.getImage().getScaledInstance(anchura, altura, java.awt.Image.SCALE_SMOOTH));
        JLabel espacioFoto = new JLabel(numenAA);
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        cuadroFecha.add(espacioFoto, constraints);

        anchura = numenDA.getIconWidth();
        altura = numenDA.getIconHeight();
        altura = (int) (altura * 80) / anchura;
        anchura = 80;
        if (altura > 80) {
            anchura = (int) (anchura * 80) / altura;
            altura = 80;
        }
        numenDA = new ImageIcon(numenDA.getImage().getScaledInstance(anchura, altura, java.awt.Image.SCALE_SMOOTH));
        espacioFoto = new JLabel(numenDA);
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        cuadroFecha.add(espacioFoto, constraints);

        anioAzteca.setFont(anioAzteca.getFont().deriveFont(10f));
        anioAzteca.setHorizontalAlignment(JButton.CENTER);
        anioAzteca.setBackground(new Color(238,238,238));
        anioAzteca.setBorderPainted(false);
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        cuadroFecha.add(anioAzteca, constraints);

        diaAzteca.setFont(diaAzteca.getFont().deriveFont(10f));
        diaAzteca.setHorizontalAlignment(JButton.CENTER);
        diaAzteca.setBackground(new Color(238,238,238));
        diaAzteca.setBorderPainted(false);
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        cuadroFecha.add(diaAzteca, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        dialogo.add(cuadroFecha, constraints);

        boton_veintena.setHorizontalAlignment(JButton.CENTER);
        boton_veintena.setBackground(new Color(238,238,238));
        boton_veintena.setBorderPainted(false);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        dialogo.add(boton_veintena, constraints);

        JButton boton_salida = new JButton("Cerrar");
        boton_salida.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                dialogo.dispose();
            }
        });
        boton_salida.setHorizontalAlignment(JButton.CENTER);
        boton_salida.setBackground(new Color(238,238,238));
        boton_salida.setBorderPainted(false);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        dialogo.add(boton_salida, constraints);

        dialogo.setTitle("Fecha azteca");
        dialogo.setResizable(false);
        dialogo.setLocation(x, y);
        dialogo.setSize(anchoCuadroDialogo, altoCuadroDialogo);
        dialogo.pack();
        dialogo.setVisible(true);
        dialogo.addMouseListener(new MouseAdapter() {
            public void mouseExited(MouseEvent e) {
//                Component component = e.getComponent();
//                if (!component.hasFocus()) System.out.println("Fuera de foco");
                Integer x = e.getX();
                Integer y = e.getY();
                if (x > 300 || y < 35) dialogo.setVisible(false);
            }
        });
    }

    private void laFechaAzteca(int anio, int mes, int dia) {
        cal.set(anio, mes, dia);
        Integer transcurridos = cal.get(Calendar.DAY_OF_YEAR);
        Integer elDiaMes = cal.get(Calendar.DAY_OF_MONTH);
        boolean bisiesto = cal.isLeapYear(anio);
        Integer glifoAnio, glifoDia, veintena;

        Integer elMes = mes + 1; // 0 corresponde a enero, 1 a febrero, ..., 11 a diciembre
        Integer anioS = -1195;
        Integer diaS = 295;
        Integer anioE = (anio + anioS) % 52;
        Integer signoA = anioE % 4;
        Integer signoNA = anioE % 13 + 1;
        Integer signoN = (((anioE % 13) * 360) + diaS+transcurridos - 1) % 13;
        Integer signoD = (14 + transcurridos) % 20;
        Integer signoM = (14 + (int) (Math.floor((transcurridos + 14) / 20))) % 19;
        if ((elMes == 3 && elDiaMes > 11) || elMes > 3) {
            signoA = (signoA + 1) % 4;
            signoNA = signoNA % 13 + 1;
            signoD = (signoD + 15) % 20;
            signoM = (14 + (int) (Math.floor((transcurridos + 29) / 20))) % 19;
            signoN = (signoN + 21) % 13;
            if (bisiesto) {
                signoD = (signoD + 19) % 20;
                signoM = (14 + (int) (Math.floor((transcurridos + 28) / 20))) % 19;
                signoN = (signoN + 12) % 13;
            }
        }
        numeralAnio = signoNA;
        glifoAnio = (signoA * 5 + 7) % 20;
        if (signoM != 18) {
            numeralDia = (signoN % 13) + 1;
            glifoDia = signoD;
        } else {
            numeralDia = 0;
            glifoDia = 0;
        }
        veintena = signoM;

        numenAA = new ImageIcon(rutan + diagonal + iconoNumen[glifoAnio] + ".png");
        if(veintena != 18)
            numenDA = new ImageIcon(rutan + diagonal + iconoNumen[glifoDia] + ".png");
        else
            numenDA = new ImageIcon("bca.png");

        String cadenaAnio = "  (A\u00f1o) " + nomNum[numeralAnio] + " " + nomGlifo[glifoAnio] + "     ";
        anioAzteca = new JButton(cadenaAnio);
        anioAzteca.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, Integer.toString(numeralAnio) + " " + nomGlifoE[glifoAnio],
                    "TRADUCCI\u00d3N", JOptionPane.PLAIN_MESSAGE);
            }
        });

        String cadenaDia;
        if(veintena != 18) {
            cadenaDia = "(D\u00eda) " + nomNum[numeralDia] + " " + nomGlifo[glifoDia] + "  ";
            diaAzteca = new JButton(cadenaDia);
        }
        else
            diaAzteca = new JButton("                              ");
        diaAzteca.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, Integer.toString(numeralDia) + " " + nomGlifoE[glifoDia],
                    "TRADUCCI\u00d3N", JOptionPane.PLAIN_MESSAGE);
            }
        });

        String cadenaVeintena = "(Veintena) " + mesAzteca[veintena];
        boton_veintena = new JButton(cadenaVeintena);
        boton_veintena.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, significadoMesAzteca[veintena], "TRADUCCI\u00d3N", JOptionPane.PLAIN_MESSAGE);
            }
        });
    }

    private void puntos(int numeral) {
        numeroAzteca = new JPanel();
        numeroAzteca.setLayout(new GridLayout(0,1));
        add(numeroAzteca);

        JLabel vacio = new JLabel("");
        vacio.setHorizontalAlignment(JLabel.LEFT);
        JLabel unPunto = new JLabel(" \u2022");
        unPunto.setHorizontalAlignment(JLabel.LEFT);
        JLabel cincoPuntos = new JLabel(" \u2022 \u2022 \u2022 \u2022 \u2022");
        cincoPuntos.setHorizontalAlignment(JLabel.LEFT);

        numeroAzteca.add(vacio);vacio = new JLabel("");
        numeroAzteca.add(vacio);vacio = new JLabel("");
        numeroAzteca.add(vacio);vacio = new JLabel("");
        numeroAzteca.add(vacio);vacio = new JLabel("");
        numeroAzteca.add(vacio);

        switch (numeral) {
            case 1:
                numeroAzteca.add(unPunto);
                numeroAzteca.add(vacio);vacio = new JLabel("             ");
                numeroAzteca.add(vacio);vacio = new JLabel("             ");
                numeroAzteca.add(vacio);vacio = new JLabel("             ");
                numeroAzteca.add(vacio);
                break;
            case 2:
                numeroAzteca.add(unPunto);unPunto = new JLabel(" \u2022");
                numeroAzteca.add(unPunto);
                numeroAzteca.add(vacio);vacio = new JLabel("             ");
                numeroAzteca.add(vacio);vacio = new JLabel("             ");
                numeroAzteca.add(vacio);
                break;
            case 3:
                numeroAzteca.add(unPunto);unPunto = new JLabel(" \u2022");
                numeroAzteca.add(unPunto);unPunto = new JLabel(" \u2022");
                numeroAzteca.add(unPunto);
                numeroAzteca.add(vacio);vacio = new JLabel("             ");
                numeroAzteca.add(vacio);
                break;
            case 4:
                numeroAzteca.add(unPunto);unPunto = new JLabel(" \u2022");
                numeroAzteca.add(unPunto);unPunto = new JLabel(" \u2022");
                numeroAzteca.add(unPunto);unPunto = new JLabel(" \u2022");
                numeroAzteca.add(unPunto);
                numeroAzteca.add(vacio);
                break;
            case 5:
                numeroAzteca.add(cincoPuntos);
                numeroAzteca.add(vacio);vacio = new JLabel("             ");
                numeroAzteca.add(vacio);vacio = new JLabel("             ");
                numeroAzteca.add(vacio);vacio = new JLabel("             ");
                numeroAzteca.add(vacio);
                break;
            case 6:
                numeroAzteca.add(cincoPuntos);
                numeroAzteca.add(unPunto);
                numeroAzteca.add(vacio);vacio = new JLabel("             ");
                numeroAzteca.add(vacio);vacio = new JLabel("             ");
                numeroAzteca.add(vacio);
                break;
            case 7:
                numeroAzteca.add(cincoPuntos);
                numeroAzteca.add(unPunto);unPunto = new JLabel(" \u2022");
                numeroAzteca.add(unPunto);
                numeroAzteca.add(vacio);vacio = new JLabel("             ");
                numeroAzteca.add(vacio);
                break;
            case 8:
                numeroAzteca.add(cincoPuntos);
                numeroAzteca.add(unPunto);unPunto = new JLabel(" \u2022");
                numeroAzteca.add(unPunto);unPunto = new JLabel(" \u2022");
                numeroAzteca.add(unPunto);
                numeroAzteca.add(vacio);
                break;
            case 9:
                numeroAzteca.add(cincoPuntos);
                numeroAzteca.add(unPunto);unPunto = new JLabel(" \u2022");
                numeroAzteca.add(unPunto);unPunto = new JLabel(" \u2022");
                numeroAzteca.add(unPunto);unPunto = new JLabel(" \u2022");
                numeroAzteca.add(unPunto);
                break;
            case 10:
                numeroAzteca.add(cincoPuntos);cincoPuntos = new JLabel(" \u2022 \u2022 \u2022 \u2022 \u2022 ");
                numeroAzteca.add(cincoPuntos);
                numeroAzteca.add(vacio);vacio = new JLabel("             ");
                numeroAzteca.add(vacio);vacio = new JLabel("             ");
                numeroAzteca.add(vacio);
                break;
            case 11:
                numeroAzteca.add(cincoPuntos);cincoPuntos = new JLabel(" \u2022 \u2022 \u2022 \u2022 \u2022 ");
                numeroAzteca.add(cincoPuntos);
                numeroAzteca.add(unPunto);
                numeroAzteca.add(vacio);vacio = new JLabel("             ");
                numeroAzteca.add(vacio);
                break;
            case 12:
                numeroAzteca.add(cincoPuntos);cincoPuntos = new JLabel(" \u2022 \u2022 \u2022 \u2022 \u2022 ");
                numeroAzteca.add(cincoPuntos);
                numeroAzteca.add(unPunto);unPunto = new JLabel(" \u2022");
                numeroAzteca.add(unPunto);
                numeroAzteca.add(vacio);
                break;
            case 13:
                numeroAzteca.add(cincoPuntos);cincoPuntos = new JLabel(" \u2022 \u2022 \u2022 \u2022 \u2022 ");
                numeroAzteca.add(cincoPuntos);
                numeroAzteca.add(unPunto);unPunto = new JLabel(" \u2022");
                numeroAzteca.add(unPunto);unPunto = new JLabel(" \u2022");
                numeroAzteca.add(unPunto);
                break;
            default:
                break;
        }
    }
    
    private void nuevoDialogo() {
        dialogo = new JDialog(CalendarioCumples.this);
        setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);

        Integer anchoCuadroDialogo = 350;
        Integer altoCuadroDialogo = 300;
        Integer x = (int) (ancho - anchoCuadroDialogo - anchoCuadroMes / 2);
        Integer y = altoCuadroMes / 2;
        
        miFolder = System.getProperty("user.dir");
        elListado(miFolder);

        final JList lista = new JList(modelo);
        lista.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Integer indice = lista.getSelectedIndex();
                    if (fotosDir[indice].endsWith("png") || fotosDir[indice].endsWith("jpg") || fotosDir[indice].endsWith("gif")) {
                        guardaCumple(indice);
                    }
                    String cadena = lista.getSelectedValue().toString();
                    miFolder = miFolder + diagonal + cadena;
                    elListado(miFolder);
                }
            }
        });
        lista.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Integer indice = lista.getSelectedIndex();
                    if (fotosDir[indice].endsWith("png") || fotosDir[indice].endsWith("jpg") || fotosDir[indice].endsWith("gif")) {
                        guardaCumple(indice);
                    }
                    String cadena = lista.getSelectedValue().toString();
                    miFolder = miFolder + diagonal + cadena;
                    elListado(miFolder);
                }
            }
        });

        JScrollPane desplazador = new JScrollPane(lista);
        desplazador.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        desplazador.setPreferredSize(new Dimension(140, 60));

        dialogo.add(desplazador, BorderLayout.WEST);
        
        JPanel panelDatos = new JPanel();

        JLabel nombreCumple = new JLabel("\u00bfQui\u00e9n cumple a\u00f1os?");
        nombreCumple.setFont(nombreCumple.getFont().deriveFont(10f));
        quienCumple = new JTextField("");
        quienCumple.setFont(quienCumple.getFont().deriveFont(11f));
        quienCumple.setMaximumSize(new Dimension(Integer.MAX_VALUE, quienCumple.getPreferredSize().height));
        quienCumple.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                quienCumple.setText("");
            }
        });
        String nombreValido = "[A-Z][a-z]+([ ][A-Z][a-z]+)*";
        quienCumple.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String nombre = quienCumple.getText();
                if (Pattern.matches(nombreValido, nombre)) {
                    nombre = nombre.replaceAll("\\s", "");
                    quienCumple.setText(nombre);
                }
                else {
                    JOptionPane.showMessageDialog(null,"S\u00f3lo nombre, nombre compuesto (s\u00f3lo un espacio intermedio), "
                                                          + linea_nueva + "nombre y apellido (un espacio intermedio),"
                                                          + linea_nueva + "comenzando con may\u00fascula, sin acentos ni 'e\u00f1es'",
                                                          "\u00a1Ojo!",JOptionPane.WARNING_MESSAGE);
                    quienCumple.setText("");
                }
            }
        });

        JLabel mesCumple = new JLabel("\u00bfEn qu\u00e9 mes?");
        mesCumple.setFont(mesCumple.getFont().deriveFont(10f));
        queMes = new JTextField();
        queMes.setText(nomMes[mes]);
        queMes.setMaximumSize(new Dimension(Integer.MAX_VALUE, queMes.getPreferredSize().height));
        queMes.setEditable(false);
        final JPopupMenu menu = new JPopupMenu();
        JMenuItem meses[] = new JMenuItem[12];
        for (int i = 0; i < 12; i++) {
            meses[i] = new JMenuItem(nomMes[i].toLowerCase());
            meses[i].setActionCommand(Integer.toString(i));
            meses[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JMenuItem seleccion = (JMenuItem) e.getSource();
                    String nombre = seleccion.getActionCommand();
                    queMes.setText(nomMes[Integer.valueOf(nombre)].toLowerCase());
                }
            });
            menu.add(meses[i]);
        }
        queMes.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == e.BUTTON1) {
                    menu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        JLabel diaCumple = new JLabel("\u00bfQu\u00e9 d\u00eda?");
        diaCumple.setFont(mesCumple.getFont().deriveFont(10f));
        queDia = new JTextField(diaElegido);
        queDia.setFont(queDia.getFont().deriveFont(11f));
        queDia.setMaximumSize(new Dimension(Integer.MAX_VALUE, queMes.getPreferredSize().height));
        queDia.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                queDia.setText("");
            }
        });
        queDia.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String numero = queDia.getText();
                if (numero.chars().allMatch(Character::isDigit)) { // por Referencia de Metodo
                    queDia.setText(numero);
                }
                else {
                    JOptionPane.showMessageDialog(null,"S\u00f3lo n\u00fameros del 1 al " + numdias[mes],"\u00a1Ojo!",JOptionPane.WARNING_MESSAGE);
                    queDia.setText(diaElegido);
                }
            }
        });

        FlowLayout pieDialogo = new FlowLayout(FlowLayout.LEADING,5,5);
        JPanel botones = new JPanel();
        botones.setLayout(pieDialogo);
        add(botones);
 
        JButton boton_regresar = new JButton("<");
        boton_regresar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                miFolder = miFolder + diagonal + "..";
                elListado(miFolder);
            }
        });
        
        JButton boton_salir = new JButton("Cerrar");
        boton_salir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                dialogo.dispose();
            }
        });
        
        botones.add(boton_regresar);
        botones.add(boton_salir);

        panelDatos.setLayout(new BoxLayout(panelDatos, BoxLayout.Y_AXIS));
        panelDatos.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panelDatos.add(nombreCumple);
        panelDatos.add(quienCumple);
        panelDatos.add(mesCumple);
        panelDatos.add(queMes);
        panelDatos.add(diaCumple);
        panelDatos.add(queDia);
        panelDatos.add(Box.createVerticalGlue());
        panelDatos.add(Box.createVerticalGlue());
        panelDatos.add(Box.createVerticalGlue());
        panelDatos.add(Box.createVerticalGlue());
        panelDatos.add(Box.createVerticalGlue());
        panelDatos.add(Box.createVerticalGlue());
        panelDatos.add(Box.createVerticalGlue());
        panelDatos.add(Box.createVerticalGlue());
        panelDatos.add(Box.createVerticalGlue());
        panelDatos.add(botones);

        dialogo.add(panelDatos, BorderLayout.EAST);
        dialogo.setTitle("Datos del cumple");
        dialogo.setResizable(false);
        dialogo.setLocation(x, y);
        dialogo.setSize(anchoCuadroDialogo, altoCuadroDialogo);
        dialogo.setVisible(true);
    }

    private void elListado(String folder) {
        modelo.clear();
        File carpeta = new File(folder);
        File[] listado = carpeta.listFiles();
        if (listado == null || listado.length == 0) {
        }
        else {
            Integer lim_sup = listado.length;
            Integer count = 0;
            Integer anchura;
            Integer altura;
            String nombreArchivo;
            File archivo;
            fotosDir = new String[lim_sup];
            for (int i = 0; i < lim_sup; i++) fotosDir[i] = "";
            for (int i = 0; i < lim_sup; i++) {
                nombreArchivo = listado[i].toString();
                if (nombreArchivo.endsWith("png") || nombreArchivo.endsWith("jpg") || nombreArchivo.endsWith("gif")) {
                    fotosDir[count] = nombreArchivo;
                    ImageIcon laFoto = new ImageIcon(nombreArchivo);
                    anchura = laFoto.getIconWidth();
                    altura = laFoto.getIconHeight();
                    altura = (int) (altura * 120) / anchura;
                    anchura = 120;
                    if (altura > 120) {
                        anchura = (int) (anchura * 120) / altura;
                        altura = 120;
                    }
                    laFoto = new ImageIcon(laFoto.getImage().getScaledInstance(anchura, altura, java.awt.Image.SCALE_SMOOTH));
                    modelo.add(count++, laFoto);
                }
                else {
                    archivo = new File(nombreArchivo);
                    if (archivo.isDirectory())
                        modelo.add(count++, archivo.getName());
                }
            }
        }
    }
    
    private void guardaCumple(int numFoto) {
        String nomFoto = quienCumple.getText() + ((Integer.valueOf(queDia.getText()) < 10) ? "0" : "") + queDia.getText()
            + fotosDir[numFoto].substring(fotosDir[numFoto].lastIndexOf("."));
        rutac = rutaf + diagonal + queMes.getText().toLowerCase();
        File archivo = new File(rutac);
        if (archivo.isDirectory()) {
            archivo = new File(rutac, nomFoto);
            String fotoVieja = archivo.getName().substring(0,archivo.getName().lastIndexOf("."));
            File archivo_viejo = new File(rutac, fotoVieja);
            if (new File(rutac, fotoVieja + ".png").exists()) archivo_viejo = new File(rutac, fotoVieja + ".png");
            if (new File(rutac, fotoVieja + ".jpg").exists()) archivo_viejo = new File(rutac, fotoVieja + ".jpg");
            if (new File(rutac, fotoVieja + ".gif").exists()) archivo_viejo = new File(rutac, fotoVieja + ".gif");
            try {
                archivo_viejo.delete();
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se por qu\u00e9, pero\n no se pudo borrar la foto",
                                              "\u00a1ERROR!", JOptionPane.PLAIN_MESSAGE);
            }
            try {
                Files.copy(Paths.get(fotosDir[numFoto]), Paths.get(rutac, nomFoto), StandardCopyOption.REPLACE_EXISTING);
                JLabel todoBien = new JLabel("Todo bien  " + Character.toString(0x1F44D));
                todoBien.setFont(todoBien.getFont().deriveFont(36f));
                JOptionPane.showMessageDialog(null, todoBien, "\u00a1AVISO!", JOptionPane.PLAIN_MESSAGE);
            }
            catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "No se pudo guardar la foto", "\u00a1ERROR!", JOptionPane.PLAIN_MESSAGE);
            }
        }
        else
            JOptionPane.showMessageDialog(null, "No se pudo guardar la foto", "\u00a1OJO!", JOptionPane.PLAIN_MESSAGE);
        dialogo.dispose();
    }

    public static void main(String args[]) {
        new CalendarioCumples();
    }
}
