package spring24;

import tools.math.Num;

class CGS_7 {
	public static void main(String[] args) {
		System.out.println((4284104 ^ 7664975) % 13444163);
		System.out.println(3967*3389);
		System.out.println(Num.modPow(4284104, 7664975, 13444163));
		
		/*
		WITH RECURSIVE modPow(base, exponent, modulus, result, mid) AS (
  SELECT 
    CAST(e.ENCRYPTEDMESSAGE AS BIGINT) AS base, -- Supposons que 'encryptedMessage' est de type num�rique compatible
    CAST(p.D AS BIGINT) AS exponent, -- La colonne D de PrivateKeys pour l'exposant
    CAST(k.N AS BIGINT) AS modulus, -- La colonne N de PublicKeys pour le modulus
    CAST(1 AS BIGINT) AS result, -- Initialise le r�sultat � 1
    e.MESSAGEINDEX as mid
  FROM EncryptedMessages e
  CROSS JOIN PrivateKeys p
  CROSS JOIN PublicKeys k
  UNION ALL
  SELECT 
    (base * base) % modulus AS base, -- Mise � jour correcte de la base
    exponent / 2 AS exponent, -- R�duit l'exposant de moiti�
    modulus, -- Le modulus reste inchang�
    CASE
      WHEN exponent % 2 = 1 THEN (result * base) % modulus ELSE result
    END AS result, -- Calcul du r�sultat interm�diaire
    mid,
  FROM modPow
  WHERE exponent > 0
)
-- S�lection des r�sultats finaux avec result < 256 pour identifier les caract�res ASCII valides
SELECT mid as MESSAGEINDEX, CHAR(mp.result) AS DECRYPTED
FROM modPow mp
WHERE exponent = 0 AND result < 256
ORDER BY mid;
		 */
	}
}


