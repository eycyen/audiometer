"""
    Hughson-Westlake esik belirleme algoritmasi.

    hasta eğer sesi duyarsa yani butona basarsa 10 desibel azalacak.
    duymazsa 5 desibel arttırılacak:
    eğer burada butona basarsa 5 desibel artmış hali hastanın eşik değeri olacak
    eğer burada da duymazsa son butona bastığı nokta eşik değer olarak tutulacak

    örnek olarak: 
    110 desibelle basladik
    hasta butona bastı 110 eşik olarak tutuluyor sonra 100 e iniyor
    yine butona bastı artık eşik 100 10 desibel daha azalttık
    90 desibelde butona basmadı eşik hala 100 test için 95 desibeli kullanıyoruz sonra burada:
    eğer: butona basarsa 95 desibel hastanın eşiği oluyor ve test için diğer frekansa geçiyoruz.
    eğer: butona basmazsa son tuttuğumuz değer olan 100 hastanın gerçek eşik değeri oluyor.
    frekanslar: 500, 1000, 2000, 4000, 8000.
    desbibeller: 110 dan başlayarak aşağıya doğru ilerleyecek
    frekanslarda 500 den başlayarak frekans arttırarak devam edeceğiz


"""
#algoritmanın tüm mantığı burada 
def hughson_westlake(frekans, hasta_esigi=None, baslangic_seviyesi=110, cevap_al=None):
    seviye = baslangic_seviyesi
    son_duyulan_seviye = None

    while True:
        if cevap_al is None:
            duydu_mu = seviye >= hasta_esigi
        else:
            duydu_mu = cevap_al(frekans, seviye)

        if duydu_mu:
            son_duyulan_seviye = seviye
            seviye -= 10
        else:
            ara_seviye = seviye + 5

            if cevap_al is None:
                ara_seviyeyi_duydu_mu = ara_seviye >= hasta_esigi
            else:
                ara_seviyeyi_duydu_mu = cevap_al(frekans, ara_seviye)

            if ara_seviyeyi_duydu_mu:
                return ara_seviye

            return son_duyulan_seviye

#burada sadece demo icin fonksiyonlar var direkt calistirip deneyebilirsin
def kullanici_cevabi_al(frekans, seviye):
    while True:
        cevap = input(f"{frekans} Hz - {seviye} dB sesi duydunuz mu (1: evet, 0: hayir): ")

        if cevap == "1":
            return True

        if cevap == "0":
            return False

        print("sadece 1 veya 0 gir")


def demo_calistir():
    frekanslar = [500, 1000, 2000, 4000, 8000]
    bulunan_esikler = {}

    print("duyulduysa 1 ,duyulmdiysa 0 gir\n")

    for frekans in frekanslar:
        print(f"{frekans} icin test")

        esik = hughson_westlake(
            frekans=frekans,
            baslangic_seviyesi=110,
            cevap_al=kullanici_cevabi_al,
        )
        bulunan_esikler[frekans] = esik

        print(f"{frekans} hz icin threshold: {esik} dB \n")

    print("tamamlandi")
    print("sonuclar:")

    for frekans, esik in bulunan_esikler.items():
        print(f"{frekans} hz: {esik} db ")

#burada printle konsola yazdırdım ben sadece görünsün diye yaptım ama
# sizin doğal olarka returnla döndürmeniz gerekecek 
# sadece algoritma kısmı mantığı kesin bir şekilde böyle olmalı
# frekans değerini göndericez proteusdaki devreey sadece o o sinyali olusturacak

if __name__ == "__main__":
    demo_calistir()
