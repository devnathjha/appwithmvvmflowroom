package com.app.trukker.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

data class Medicine(
    val problems: List<Problem>
)

data class Problem(
    val Asthma: List<Asthma>,
    val Diabetes: List<Diabete>
)

 class Asthma

data class Diabete(
    val labs: List<Lab>,
    val medications: List<Medication>
)

data class Lab(
    val missing_field: String
)

data class Medication(
    val medicationsClasses: List<MedicationsClasse>
)

data class MedicationsClasse(
    val className: List<ClassName>,
    val className2: List<ClassName>
)

data class ClassName(
    val associatedDrug: List<AssociatedDrug>,
    @field:Json(name="associatedDrug#2")val associatedDrug2: List<AssociatedDrug>
)

@Parcelize
data class AssociatedDrug(
    val dose: String?,
    val name: String?,
    val strength: String?
):Parcelable