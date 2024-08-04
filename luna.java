public class luna {
    int day, month, year;
    double jd, frac;

    public double julday (int id, int mm, int iyyy) {
        double jy=0, jm=0, jul=0;
        if (iyyy < 0) { iyyy+=1; }
        if (mm > 2) {
            jy=iyyy;
            jm=1.0*mm+1;
        }
        else {
            jy=iyyy-1;
            jm=1.0*mm+13;
        }
        double j1=1.0*Math.floor(365.25*jy);
        double j2=1.0*j1+Math.floor(30.6001*jm);
        double j3=1.0*j2+1.0*id;
        double j4=1.0*j3+1720995;
        if (id+31*(mm+12*iyyy) >= (15+31*(10+12*1582))) {
            double ja=1.0*Math.floor(0.01*jy);
            double jb=1.0*Math.floor(0.25*ja);
            double j5=1.0*j4+2;
            double j6=1.0*j5-ja;
            jul=1.0*j6+jb;
        }
        else { jul=j4; }
        return jul;
    }

    public void caldat (double julian) {
        double ja;
        julian=0+julian;
        if (julian >= 2299161) {
            double jalpha=Math.floor((1.0*(julian-1867216)-0.25)/36524.25);
            ja=1*julian+1+jalpha-Math.floor(0.25*jalpha);
        }
        else if (julian < 0) {
            ja=julian+36525*(1-julian/36525);
        }
        else {
            ja=julian;
        }
        double jb=ja+1524;
        double jc=Math.floor(6680.0+((jb-2439870)-122.1)/365.25);
        double jd=365*jc+Math.floor(0.25*jc);
        double je=Math.floor((jb-jd)/30.6001);
        day=(int)(jb-jd-Math.floor(30.6001*je));
        month=(int)(je-1);
        if (month > 12) { month-=12; }
        year=(int)(jc-4715);
        if (month > 2) { --year; }
        if (year <= 0) { --year; }
        if (julian < 0) {
            year-=100*(1-julian/36525);
        }
    }

    public void flmoon (int n, int nph) {
        double RAD=3.14159265/180.0;
        double c=n+nph/4.0;
        double t=c/1236.85;
        double t2=t*t;
        double as=359.2242+29.105356*c;
        double am=306.0253+385.816918*c+0.010730*t2;
        jd=2415020+28*n+7*nph;
        double xtra=0.75933+1.53058868*c+((1.178e-4)-(1.55e-7)*t)*t2;
        if (nph == 0 || nph == 2)
            xtra+=(0.1734-3.93e-4*t)*Math.sin(RAD*as)-0.4068*Math.sin(RAD*am);
        else if (nph == 1 || nph == 3)
            xtra+=(0.1721-4.0e-4*t)*Math.sin(RAD*as)-0.6280*Math.sin(RAD*am);
        double i=((xtra > 0.0) ? Math.floor(xtra) : Math.ceil(xtra-1.0));
        jd+=i;
        frac=xtra-i;
    }
}
