#include <jni.h>
#include <vector>
#include <cmath>

//-----------------------------1----------------------------------

/*
  Розділ 1. Сторінка 1.


  En – власний показник ефективності n-го завдання системи ХБРЯ захисту;

  R0 – допустимий ризик досягнення мети бойових дій щодо n-го завдання системи
  ХБРЯ захисту;

  Rn  – імовірний ризик досягнення мети бойових дій до планування n-го завдання
  системи ХБРЯ захисту або очікуваний ризик досягнення мети бойових дій після
  планування n-го завдання системи ХБРЯ захисту.
*/

extern "C" JNIEXPORT jfloat JNICALL Java_ua_nure_cbrnprotector_ui_En_EnViewModel_En(
        JNIEnv *env, jobject /* this */, jfloat Rn, jfloat R0) {
    return 1 - (Rn - R0) / Rn;
}

/*
  Розділ 1.1. Сторінка 4.


  Rny – показник ймовірного ризику недостатнього рівня ураження об’єктів
  противника РПВ;

  Py – показник імовірності влучення ТББ у об’єкти противника;

  Vy – показник ступеня ураження об’єктів противника РПВ.
*/

extern "C" JNIEXPORT jfloat JNICALL Java_ua_nure_cbrnprotector_ui_Rnu_RnuViewModel_Rny(
        JNIEnv *env, jobject /* this */, jfloat Py, jfloat Vy) {
    return 1 - Py * Vy;
}

/*
  Розділ 1.2. Сторінка 5.


  R0 – показник допустимого ризику досягнення мети бойових дій щодо втрати життя
і здоров’я особового складу в умовах РХ зараження;

  P0 – показник імовірності РХ зараження особового складу підрозділів ;

  V0 – показник максимально допустимої уразливості особового складу підрозділів
в умовах РХ зараження.
*/

extern "C" JNIEXPORT jfloat JNICALL Java_ua_nure_cbrnprotector_ui_R0_R0ViewModel_R0(
        JNIEnv *env, jobject /* this */, jfloat P0, jfloat V0) {
    return P0 * V0;
}

/*
  Розділ 1.2. Сторінка 6.


  V0 – показник максимально допустимої уразливості особового складу підрозділів
  в умовах РХ зараження;

  N0_ivt – кількість особового складу i-ї військової частини , який ймовірно
  втратить життя та здоров’я в умовах РХ зараження;

  N_oc – загальна кількість особового складу .
*/

extern "C" JNIEXPORT jfloat JNICALL
Java_ua_nure_cbrnprotector_ui_V0_V0ViewModel_V0(
        JNIEnv *env,
        jobject /* this */,
        jint sum_N_ivt,
        jint N_oc) {
    return static_cast<float>(sum_N_ivt) / N_oc;
}

/*
  Розділ 2. Сторінка 7.

  Rrhz – показник ймовірного ризику досягнення мети бойових дій щодо втрати
  життя і здоров’я особового складу підрозділів в умовах РХ зараження;

  Prhz – показник імовірності РХ зараження особового складу підрозділів ;

  Vrhz – показник уразливості особового складу підрозділів в умовах РХ
  зараження.
*/

extern "C" JNIEXPORT jfloat JNICALL
Java_ua_nure_cbrnprotector_ui_Rrhz_RrhzViewModel_Rrhz(JNIEnv *env, jobject /* this */, jfloat Prhz,
                                             jfloat Vrhz) {
    return Prhz * Vrhz;
}


/*
  Розділ 2.1. Сторінка 7.

  Увага!!!!

  Функція Pjirhz використовується в функції Prhz.

  Prhz – показник імовірності РХ зараження особового складу підрозділів ;

  Pjirhz – показник імовірності реалізації j-х загроз РХ зараження i-ї
  військової частини за наявною інформацією старшого начальника, даними
  розвідки;

  N_rhz – кількість і-х військових частин де реалізуються загрози
  РХ зараження.

  D_j_irhz – кількість j-х загроз РХ зараження і-ї військової частини за даними
  розвідки;

  n_irhz – загальна кількість визначених загроз, реалізація яких 100 % призведе
  до втрат особового складу і-ї військової частини в умовах РХ зараження

*/

extern "C"
JNIEXPORT jfloat JNICALL
Java_ua_nure_cbrnprotector_MainActivity_Pjirhz(JNIEnv *env, jobject /* this */, jint D_j_irz,
                                               jint n_irhz) {
    return static_cast<jfloat>(D_j_irz) / n_irhz;
}

extern "C"
JNIEXPORT jfloat JNICALL
Java_ua_nure_cbrnprotector_ui_Prhz_PrhzViewModel_Prhz(JNIEnv *env, jobject /* this */, jintArray D_j_irz,
                                             jintArray n_irhz, jint N_rhz) {
    jfloat sum = 0;
    jboolean isCopy;
    jint *D_j_irz_ptr = env->GetIntArrayElements(D_j_irz, &isCopy);
    jint *n_irhz_ptr = env->GetIntArrayElements(n_irhz, &isCopy);
    for (int i = 0; i < N_rhz; i++) {
        sum += static_cast<jfloat>(D_j_irz_ptr[i]) / n_irhz_ptr[i];
    }
    env->ReleaseIntArrayElements(D_j_irz, D_j_irz_ptr, JNI_ABORT);
    env->ReleaseIntArrayElements(n_irhz, n_irhz_ptr, JNI_ABORT);
    return sum / N_rhz;
}

/*
  Розділ 2.1. Сторінка 7.

  Увага!!!!

  Функція N200, N300 використовується в функції Vrhz.

  N200 – можливі втрати життя особового складу і-ї військової частини в умовах
  РХ зараження;

  N300 – можливі втрати здоров’я особового складу і-ї військової частини в
  умовах РХ зараження;

  N_oc – загальна кількість особового складу .

  N_rhz – кількість і-х військових частин де реалізуються загрози
  РХ зараження.

  Vrhz – показника уразливості особового складу підрозділів в умовах РХ
  зараження

*/

extern "C" JNIEXPORT jint JNICALL
Java_ua_nure_cbrnprotector_ui_Vrhz_VrhzViewModel_N200(JNIEnv *env, jobject thiz, jintArray N_200_ios,
                                             jint N_rhz) {
    jint *N_200_ios_ptr = env->GetIntArrayElements(N_200_ios, 0);
    jint sum200 = 0;
    for (int i = 0; i < N_rhz; i++) {
        sum200 += N_200_ios_ptr[i];
    }
    env->ReleaseIntArrayElements(N_200_ios, N_200_ios_ptr, 0);
    return sum200;
}

extern "C" JNIEXPORT jint JNICALL
Java_ua_nure_cbrnprotector_ui_Vrhz_VrhzViewModel_N300(JNIEnv *env, jobject thiz, jintArray N_300_ios,
                                             jint N_rhz) {
    jint *N_300_ios_ptr = env->GetIntArrayElements(N_300_ios, 0);
    jint sum300 = 0;
    for (int i = 0; i < N_rhz; i++) {
        sum300 += N_300_ios_ptr[i];
    }
    env->ReleaseIntArrayElements(N_300_ios, N_300_ios_ptr, 0);
    return sum300;
}

extern "C" JNIEXPORT jfloat JNICALL
Java_ua_nure_cbrnprotector_ui_Vrhz_VrhzViewModel_Vrhz(JNIEnv *env, jobject thiz, jint N200, jint N300,
                                             jint N_oc) {
    return static_cast<float>(N200 + N300) / N_oc;
}

/*
  Сторінка 8. Пункт 2.3

  Rrhz_Critical – показник критичного ризику досягнення мети бойових дій щодо
  втрати життя і здоров’я особового складу в умовах РХ зараження;

  R0 – показник допустимого ризику досягнення мети бойових дій щодо втрати життя
  і здоров’я особового складу в умовах РХ зараження;

  Rrhz – показник ймовірного ризику досягнення мети бойових дій щодо втрати
  життя і здоров’я особового складу підрозділів в умовах РХ зараження;

*/

extern "C" JNIEXPORT jfloat JNICALL
Java_ua_nure_cbrnprotector_ui_RrhzCrit_RrhzCritViewModel_RrhzCritical(JNIEnv *env, jobject thiz, jfloat Rrhz,
                                                      jfloat R0) {
    return Rrhz - R0;
}

/*
  Сторінка 8. Пункт 2.3

  Ehbrya_necessary – обчислення необхідної ефективності виконання заходів ХБРЯ
  захисту:

  R0 – показник допустимого ризику досягнення мети бойових дій щодо втрати життя
  і здоров’я особового складу в умовах РХ зараження;

*/

extern "C" JNIEXPORT jfloat JNICALL
Java_ua_nure_cbrnprotector_ui_EhbryaNec_EhbryaNecViewModel_EhbryaNecessary(JNIEnv *env, jobject thiz, float R0) {
    return (1 - R0);
}

/*
  Сторінка 8. Пункт 2.3

  Ehbrya_0 - обчислення ефективності виконання заходів ХБРЯ захисту військовими
  частинами без підрозділів РХБ захисту

  Rrhz_Critical – показник критичного ризику досягнення мети бойових дій щодо
  втрати життя і здоров’я особового складу в умовах РХ зараження;

  Rrhz – показник ймовірного ризику досягнення мети бойових дій щодо втрати
  життя і здоров’я особового складу підрозділів в умовах РХ зараження;

*/

extern "C" JNIEXPORT jfloat JNICALL
Java_ua_nure_cbrnprotector_ui_Ehbrya0_Ehbrya0ViewModel_Ehbrya0(JNIEnv *env, jobject thiz, jfloat Rrhz_Critical,
                                                 jfloat Rrhz) {
    return 1 - (Rrhz_Critical / Rrhz);
}

/*

  Сторінка 8. Пункт 2.5. Формула 15.

  R_hbrya_rhz_och – показник очікуваного ризику досягнення мети бойових дій щодо
  втрати здоров’я особового складу підрозділів в умовах РХ зараження після
  спланованих заходів ХБРЯ захисту;

  P_hbrya_rhz_och – показник очікуваної імовірності РХ зараження особового
  складу підрозділів після спланованих заходів ХБРЯ захисту;

  V_hbrya_rhz_och– показник очікуваної уразливості особового складу підрозділів
  в умовах РХ зараження після спланованих заходів ХБРЯ захисту.

*/

extern "C" JNIEXPORT jfloat JNICALL
Java_ua_nure_cbrnprotector_MainActivity_R_hbrya_rhz_och(JNIEnv *env, jobject thiz,
                                                        jfloat P_hbrya_rhz_och,
                                                        jfloat V_hbrya_rhz_och) {
    return P_hbrya_rhz_och * V_hbrya_rhz_och;
}

/*

  Сторінка 9. Пункт 2.6. Формула 18.

*/

extern "C" JNIEXPORT jint JNICALL
Java_ua_nure_cbrnprotector_ui_Wvo_WvoViewModel_WVO(JNIEnv *env, jobject thiz,
                                             jint w_rhr,
                                             jint w_rhk,
                                             jint w0) {
    return w_rhr + w_rhk + w0;
}

/*

  Сторінка 9. Пункт 2.6. Формула 19.

*/

extern "C" JNIEXPORT jint JNICALL
Java_ua_nure_cbrnprotector_ui_Wpg_WpgViewModel_WPJ(JNIEnv *env, jobject thiz,
                                             jint w_op,
                                             jint w_zikz,
                                             jint w_poz) {
    return w_op + w_poz + w_zikz;
}

/*

  Сторінка 9. Пункт 2.6. Формула 20.

*/

extern "C" JNIEXPORT jint JNICALL
Java_ua_nure_cbrnprotector_ui_Wln_WlnViewModel_WLN(JNIEnv *env, jobject thiz,
                                             jint w_os,
                                             jint w_ovt,
                                             jint w_m) {
    return w_os + w_ovt + w_m;
}

/*

  Сторінка 9. Пункт 2.6. Формула 17.

*/


extern "C" JNIEXPORT jint JNICALL
Java_ua_nure_cbrnprotector_ui_Whbrya_WhbryaViewModel_WHBRYA(JNIEnv *env, jobject thiz,
                                                jint W_VO,
                                                jint W_PG,
                                                jint W_LN) {
    return W_VO + W_PG + W_LN;
}

/*

  Сторінка 10. Пункт 2.7. Формула 21.

*/

extern "C" JNIEXPORT jint JNICALL
Java_ua_nure_cbrnprotector_MainActivity_N_j_i(JNIEnv *env, jobject thiz, jintArray w_i,
                                              jintArray a_i, jint i_rhb) {
    jint *w_i_ptr = env->GetIntArrayElements(w_i, 0);
    jint *a_i_ptr = env->GetIntArrayElements(a_i, 0);
    jint sum = 0;
    for (int i = 0; i < i_rhb; i++) {
        sum += w_i_ptr[i] / a_i_ptr[i];
    }
    env->ReleaseIntArrayElements(w_i, w_i_ptr, 0);
    env->ReleaseIntArrayElements(a_i, a_i_ptr, 0);
    return sum;
}

/*

  Сторінка 10. Пункт 2.7. Формула 22.

*/

jint N_j_i(JNIEnv *env, jintArray w_i, jintArray a_i, jint i_rhb) {
    jint *w_i_ptr = env->GetIntArrayElements(w_i, 0);
    jint *a_i_ptr = env->GetIntArrayElements(a_i, 0);
    jint sum = 0;
    for (int i = 0; i < i_rhb; i++) {
        sum += w_i_ptr[i] / a_i_ptr[i];
    }
    env->ReleaseIntArrayElements(w_i, w_i_ptr, 0);
    env->ReleaseIntArrayElements(a_i, a_i_ptr, 0);
    return sum;
}

extern "C" JNIEXPORT jint JNICALL
Java_ua_nure_cbrnprotector_ui_Kn_KnViewModel_Kn(JNIEnv *env, jobject thiz, jintArray w_i, jintArray a_i,
                                            jintArray N_i_um) {
    jint *w_i_ptr = env->GetIntArrayElements(w_i, 0);
    jint *a_i_ptr = env->GetIntArrayElements(a_i, 0);
    jint *N_i_um_ptr = env->GetIntArrayElements(N_i_um, 0);
    jint K = 0;
    int size = env->GetArrayLength(w_i);
    for (int i = 0; i < size; i++) {
        jint N_i_j = N_j_i(env, w_i, a_i, i);
        K += N_i_j / N_i_um_ptr[i];
    }
    env->ReleaseIntArrayElements(w_i, w_i_ptr, 0);
    env->ReleaseIntArrayElements(a_i, a_i_ptr, 0);
    env->ReleaseIntArrayElements(N_i_um, N_i_um_ptr, 0);
    return K;
}

/*

  Сторінка 10. Пункт 2.8. Формула 23.

*/

extern "C" JNIEXPORT jint JNICALL
Java_ua_nure_cbrnprotector_ui_Cn_CnViewModel_Cn(JNIEnv *env, jobject thiz, jintArray N_i,
                                            jintArray c_i) {
    jint *N_i_ptr = env->GetIntArrayElements(N_i, 0);
    jint *c_i_ptr = env->GetIntArrayElements(c_i, 0);
    jint C = 0;
    int size = env->GetArrayLength(N_i);
    for (int i = 0; i < size; i++) {
        C += N_i_ptr[i] * c_i_ptr[i];
    }
    env->ReleaseIntArrayElements(N_i, N_i_ptr, 0);
    env->ReleaseIntArrayElements(c_i, c_i_ptr, 0);
    return C;
}

/*

  Сторінка 11. Пункт 2.9. Формула 24.

*/


extern "C" JNIEXPORT jfloat JNICALL
Java_ua_nure_cbrnprotector_ui_Ehbrya_EhbryaViewModel_Ehbrya(JNIEnv *env, jobject thiz,
                                               jfloat Rrhz_och_Critical,
                                               jfloat Rrhz_och) {
    return Rrhz_och_Critical / Rrhz_och;
}

/*

  Сторінка 11. Пункт 2.10. Формула 25.

*/

extern "C" JNIEXPORT jfloat JNICALL
Java_ua_nure_cbrnprotector_MainActivity_V_hbrya_rhz_on(JNIEnv *env, jobject thiz,
                                                       jfloat N_yr,
                                                       jfloat N_oc) {
    return N_yr / N_oc;
}

/*

  Сторінка 11. Пункт 3. Формула 26

*/

extern "C" JNIEXPORT jfloat JNICALL
Java_ua_nure_cbrnprotector_ui_R0mod_R0ModViewModel_R0Mod(JNIEnv *env, jobject thiz,
                                                    jfloat P0,
                                                    jfloat V0) {
    return P0 * V0;
}

/*

  Сторінка 11. Пункт 3. Формула 27

*/

extern "C" JNIEXPORT jfloat JNICALL
Java_ua_nure_cbrnprotector_ui_V0Mod_V0ModViewModel_V0Mod(JNIEnv *env, jobject thiz, jintArray N_iv_0,
                                                    jint N_ob) {
    jint *N_iv_0_ptr = env->GetIntArrayElements(N_iv_0, 0);
    jint sum = 0;
    int size = env->GetArrayLength(N_iv_0);
    for (int i = 0; i < size; i++) {
        sum += N_iv_0_ptr[i];
    }
    env->ReleaseIntArrayElements(N_iv_0, N_iv_0_ptr, 0);
    return static_cast<float>(sum) / N_ob;
}

/*

  Сторінка 11. Пункт 3.1. Формула 28

*/

extern "C" JNIEXPORT jfloat JNICALL
Java_ua_nure_cbrnprotector_ui_RV_RVViewModel_Rv(JNIEnv *env, jobject thiz,
                                           jfloat Pv,
                                           jfloat Vv) {
    return Pv * Vv;
}

/*

  Сторінка 11. Пункт 3.1.1. Формула 29, 30

*/

jfloat P_j_iv(jint D_j_iv, jint n_iv) {
    return static_cast<float>(D_j_iv) / n_iv;
}

extern "C" JNIEXPORT jfloat JNICALL
Java_ua_nure_cbrnprotector_MainActivity_Pv(JNIEnv *env, jobject thiz, jintArray D_j_iv,
                                           jintArray n_irhz, jint Nv) {
    jint *D_j_iv_ptr = env->GetIntArrayElements(D_j_iv, 0);
    jint *n_irhz_ptr = env->GetIntArrayElements(n_irhz, 0);
    jfloat sum = 0.0;
    for (int i = 0; i < Nv; i++) {
        sum += P_j_iv(D_j_iv_ptr[i], n_irhz_ptr[i]);
    }
    env->ReleaseIntArrayElements(D_j_iv, D_j_iv_ptr, 0);
    env->ReleaseIntArrayElements(n_irhz, n_irhz_ptr, 0);
    return sum / Nv;
}

/*

  Сторінка 13. Пункт 3.1.2. Формула 31

*/

extern "C" JNIEXPORT jfloat JNICALL
Java_ua_nure_cbrnprotector_MainActivity_Vv(JNIEnv *env, jobject thiz, jintArray N_iv, jint N_ob) {
    jint *N_iv_ptr = env->GetIntArrayElements(N_iv, 0);
    jfloat sum = 0.0;
    int size = env->GetArrayLength(N_iv);
    for (int i = 0; i < size; i++) {
        sum += N_iv_ptr[i];
    }
    env->ReleaseIntArrayElements(N_iv, N_iv_ptr, 0);
    return sum / N_ob;
}

/*

  Сторінка 14. Пункт 3.1.2. Формула 32

*/

extern "C" JNIEXPORT jfloat JNICALL
Java_ua_nure_cbrnprotector_MainActivity_K_iob(JNIEnv *env, jobject thiz,
                                              jfloat L_if,
                                              jfloat L_iob) {
    return std::abs(L_if - L_iob) / std::max(L_if, L_iob);
}

/*

  Сторінка 14. Пункт 3.1.3. Формула 33

*/

extern "C" JNIEXPORT jfloat JNICALL
Java_ua_nure_cbrnprotector_MainActivity_Rv_critical(JNIEnv *env, jobject thiz,
                                                    jfloat Rv,
                                                    jfloat R0) {
    return Rv - R0;
}

/*

  Сторінка 14. Пункт 3.1.3. Формула 34

*/

extern "C" JNIEXPORT jfloat JNICALL
Java_ua_nure_cbrnprotector_MainActivity_E_neo_am(JNIEnv *env, jobject thiz,
                                                 jfloat R0) {
    return 1 - R0;
}

/*

  Сторінка 14. Пункт 3.1.3. Формула 35

*/

extern "C" JNIEXPORT jfloat JNICALL
Java_ua_nure_cbrnprotector_MainActivity_E_0_am(JNIEnv *env, jobject thiz,
                                               jfloat Rv_critical,
                                               float Rv) {
    return 1 - (Rv_critical / Rv);
}

/*

  Сторінка 14. Пункт 3.2 Формула 36

  Вимоги:
  1. Розмір масиву D_j_iv повинен бути такий самий як розмір масиву nzv
  2. D_j_iv[i] <= nzv[i]
  3. За умови виконання пункту 3, кількість ітерацій (N_v_h) визначається за допомогою довжини
  будь-якого з масивів.

  На екрані повинно бути поле для:
  1. D_j_iv - сума кількості j-х загроз виявлення і-го об’єкту за даними розвідки;
  2. nzv - загальна кількість визначених загроз, реалізація яких 100 % призведе до виявлення і-го
  об’єкта.
  3. n - кількість хибних районів АМ.
  4. N_h_v - кількість макетів об’єктів в хибному район АМ.

*/

jfloat P_iv_j(jint D_j_iv, jint nzv) {
    return static_cast<float>(D_j_iv) / nzv;
}

extern "C" JNIEXPORT jfloat JNICALL
Java_ua_nure_cbrnprotector_MainActivity_P_v_AM(JNIEnv *env, jobject thiz, jintArray D_j_iv,
                                               jintArray nzv, jint N_v, jint n, jint N_h_v) {
    jint *D_j_iv_ptr = env->GetIntArrayElements(D_j_iv, 0);
    jint *nzv_ptr = env->GetIntArrayElements(nzv, 0);
    jfloat sum = 0.0;
    for (int i = 0; i < N_v; i++) {
        sum += P_iv_j(D_j_iv_ptr[i], nzv_ptr[i]);
    }
    env->ReleaseIntArrayElements(D_j_iv, D_j_iv_ptr, 0);
    env->ReleaseIntArrayElements(nzv, nzv_ptr, 0);
    return sum / (N_v + n * N_h_v);
}