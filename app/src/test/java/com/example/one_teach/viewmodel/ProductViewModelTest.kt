package com.example.one_teach.viewmodel

import com.example.one_teach.model.Usuario
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ProfileViewModelTest {

    private lateinit var vm: ProfileViewModel

    @Before
    fun setup() {
        vm = ProfileViewModel()
    }

    private fun dummyUser(
        fullname: String = "Juan Pérez",
        rut: String = "11111111-1",
        email: String = "juan@example.com",
        phone: String = "912345678",
        direccion: String = "Calle Falsa 123",
        region: String = "RM",
        comuna: String = "Santiago"
    ) = Usuario(
        fullname = fullname,
        rut = rut,
        email = email,
        phone = phone,
        direccion = direccion,
        region = region,
        comuna = comuna
    )

    @Test
    fun `al iniciar ui y lista de usuarios estan vacios`() {
        // ui vacío
        assertEquals("", vm.ui.fullname)
        assertEquals("", vm.ui.email)
        assertEquals("", vm.ui.rut)
        assertEquals("", vm.ui.phone)
        assertEquals("", vm.ui.direccion)
        assertEquals("", vm.ui.region)
        assertEquals("", vm.ui.comuna)
        assertFalse(vm.ui.editing)

        // lista vacía
        assertTrue(vm.users.value.isEmpty())
    }

    @Test
    fun `addOrSelectUser agrega usuario nuevo y lo carga en la ui`() {
        val u = dummyUser()

        vm.addOrSelectUser(u)

        // se agregó a la lista
        assertEquals(1, vm.users.value.size)
        assertEquals(u.email, vm.users.value.first().email)

        // se cargó a la UI
        assertEquals(u.fullname, vm.ui.fullname)
        assertEquals(u.email, vm.ui.email)
        assertEquals(u.rut, vm.ui.rut)
        assertEquals(u.phone, vm.ui.phone)
        assertEquals(u.direccion, vm.ui.direccion)
        assertEquals(u.region, vm.ui.region)
        assertEquals(u.comuna, vm.ui.comuna)
    }

    @Test
    fun `addOrSelectUser con mismo email actualiza al usuario en vez de duplicar`() {
        val original = dummyUser(
            fullname = "Juan Original",
            phone = "900000000"
        )
        val actualizado = dummyUser(
            fullname = "Juan Actualizado",
            phone = "988888888"
        )

        vm.addOrSelectUser(original)
        vm.addOrSelectUser(actualizado)

        // sigue habiendo solo 1 usuario
        assertEquals(1, vm.users.value.size)

        val stored = vm.users.value.first()
        assertEquals("Juan Actualizado", stored.fullname)
        assertEquals("988888888", stored.phone)
        // el email y rut se mantienen
        assertEquals(original.email, stored.email)
        assertEquals(original.rut, stored.rut)
    }

    @Test
    fun `saveChanges actualiza datos del usuario y desactiva modo edicion`() {
        val u = dummyUser()
        vm.addOrSelectUser(u)

        // ponemos la UI en modo edición y cambiamos algunos campos
        vm.setEditing(true)
        vm.updateFullname("Nombre Nuevo")
        vm.updatePhone("977777777")
        vm.updateDireccion("Otra dirección 456")
        vm.updateRegion("Biobío")
        vm.updateComuna("Concepción")

        vm.saveChanges()

        // se salió del modo edición
        assertFalse(vm.ui.editing)

        // se actualizaron los datos del usuario en la lista
        val stored = vm.users.value.first()
        assertEquals("Nombre Nuevo", stored.fullname)
        assertEquals("977777777", stored.phone)
        assertEquals("Otra dirección 456", stored.direccion)
        assertEquals("Biobío", stored.region)
        assertEquals("Concepción", stored.comuna)

        // rut y email no deberían cambiar
        assertEquals(u.rut, stored.rut)
        assertEquals(u.email, stored.email)
    }

    @Test
    fun `clearUsers limpia lista y resetea ui`() {
        vm.addOrSelectUser(dummyUser())
        assertTrue(vm.users.value.isNotEmpty())

        vm.clearUsers()

        assertTrue(vm.users.value.isEmpty())
        // ui reseteada
        assertEquals("", vm.ui.fullname)
        assertEquals("", vm.ui.email)
        assertEquals("", vm.ui.rut)
    }
}
